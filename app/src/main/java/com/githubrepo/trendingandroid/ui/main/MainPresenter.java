package com.githubrepo.trendingandroid.ui.main;

import com.githubrepo.trendingandroid.data.DataManager;
import com.githubrepo.trendingandroid.data.model.User;
import com.githubrepo.trendingandroid.ui.base.presenter.MvpLceRxPresenter;
import com.githubrepo.mvp.common.MvpPresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainPresenter extends MvpLceRxPresenter<MainMvpView, User> implements MvpPresenter<MainMvpView> {
    private final DataManager dataManager;
    private CompositeSubscription mSubscriptions;

    @Inject
    public MainPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView view) {
        super.attachView(view);
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(dataManager.getRxBus().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {

                    }
                }));
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mSubscriptions.unsubscribe();
    }

    public void getAccessToken(String code) {
        subscribe(dataManager.getAccessToken(code), false);
    }

    public void checkUserStatus() {
        User user = null;
        if (null != dataManager.getPreferencesHelper().getAccessToken()) {
            //if token is exist, user is exist
            user = new User();
            user.setLogin(dataManager.getPreferencesHelper().getUserLogin());
            user.setEmail(dataManager.getPreferencesHelper().getUserEmail());
            user.setAvatar_url(dataManager.getPreferencesHelper().getUserAvatar());
        }

        getView().setData(user);
    }
}
