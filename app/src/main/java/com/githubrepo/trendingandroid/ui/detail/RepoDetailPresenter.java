package com.githubrepo.trendingandroid.ui.detail;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.githubrepo.trendingandroid.data.DataManager;
import com.githubrepo.trendingandroid.ui.base.presenter.MvpLceRxPresenter;
import com.githubrepo.trendingandroid.util.HttpStatus;
import com.githubrepo.mvp.common.MvpPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;
import timber.log.Timber;

public class RepoDetailPresenter extends MvpLceRxPresenter<RepoDetailMvpView, String> implements MvpPresenter<RepoDetailMvpView> {
    private final DataManager dataManager;
    private String cssFile = "file:///android_asset/markdown_css_themes/classic.css";

    private Subscriber<Response<Void>> mCheckStar;
    private StarSubscriber mStar;

    @Inject
    public RepoDetailPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getReadme(final String owner, final String repo, final boolean pullToRefresh) {
        Timber.d("### getReadme owner:%s, repo: %s", owner, repo);
        subscribe(dataManager.getReadme(owner, repo, cssFile), pullToRefresh);
    }


    @Override
    protected void unsubscribe() {
        super.unsubscribe();
        if (null != mCheckStar && mCheckStar.isUnsubscribed()) {
            mCheckStar.unsubscribe();
        }

        if (null != mStar && mStar.isUnsubscribed()) {
            mStar.unsubscribe();
        }

        mStar = null;
        mCheckStar = null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        unsubscribe();
    }

    @RxLogSubscriber
    private class StarSubscriber extends Subscriber<Response<Void>> {
        private boolean isStar;

        public StarSubscriber(final boolean isStar) {
            this.isStar = isStar;
        }

        @Override
        public void onCompleted() {
            if (isViewAttached()) {
                getView().showContent();
            }
            unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                getView().showError(e, true);
            }
            unsubscribe();
        }

        @Override
        public void onNext(Response<Void> response) {
            if (null != response && response.code() == HttpStatus.HTTP_NO_CONTENT) {
                if (isStar) {
                    getView().starStatus(true);
                } else {
                    getView().starStatus(false);
                }
            } else {
                if (isStar) {
                    getView().starStatus(false);
                } else {
                    getView().starStatus(true);
                }
            }
        }
    }
}
