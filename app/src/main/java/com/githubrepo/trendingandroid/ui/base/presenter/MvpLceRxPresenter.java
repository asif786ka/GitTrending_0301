package com.githubrepo.trendingandroid.ui.base.presenter;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.githubrepo.mvp.common.MvpBasePresenter;
import com.githubrepo.mvp.common.MvpPresenter;
import com.githubrepo.mvp.common.lce.MvpLceView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A presenter for RxJava, that assumes that only one Observable is subscribed by this presenter.
 * The idea is, that you make your (chain of) Observable and pass it to {@link
 * #subscribe(Observable, boolean)}. The presenter internally subscribes himself as Subscriber to
 * the
 * observable
 * (which executes the observable).
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public abstract class MvpLceRxPresenter<V extends MvpLceView<M>, M>
        extends MvpBasePresenter<V> implements MvpPresenter<V> {

    protected Subscriber<M> subscriber;

    /**
     * Unsubscribes the subscriber and set it to null
     */
    protected void unsubscribe() {
        if (null != subscriber && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }

        subscriber = null;
    }

    /**
     * Subscribes the presenter himself as subscriber on the observable
     *
     * @param observable    The observable to subscribe
     * @param pullToRefresh Pull to refresh?
     */
    public void subscribe(final Observable<M> observable, final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        unsubscribe();

        subscriber = new RxSubscriber(pullToRefresh);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @RxLogSubscriber
    private class RxSubscriber extends Subscriber<M> {
        final private boolean ptr;

        public RxSubscriber(final boolean pullToRefresh) {
            this.ptr = pullToRefresh;
        }

        @Override
        public void onCompleted() {
            MvpLceRxPresenter.this.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            MvpLceRxPresenter.this.onError(e, ptr);
        }

        @Override
        public void onNext(M m) {
            MvpLceRxPresenter.this.onNext(m);
        }
    }

    protected void onCompleted() {
        if (isViewAttached()) {
            getView().showContent();
        }
        unsubscribe();
    }

    protected void onError(Throwable e, boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showError(e, pullToRefresh);
        }
        unsubscribe();
    }

    protected void onNext(M data) {
        if (isViewAttached()) {
            getView().setData(data);
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            unsubscribe();
        }
    }
}
