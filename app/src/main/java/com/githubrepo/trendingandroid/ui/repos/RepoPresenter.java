package com.githubrepo.trendingandroid.ui.repos;

import com.githubrepo.trendingandroid.data.DataManager;
import com.githubrepo.trendingandroid.data.model.Repo;
import com.githubrepo.trendingandroid.data.model.WrapList;
import com.githubrepo.trendingandroid.ui.base.presenter.MvpLceRxPresenter;
import com.githubrepo.mvp.common.MvpPresenter;

import javax.inject.Inject;

import timber.log.Timber;


public class RepoPresenter extends MvpLceRxPresenter<RepoMvpView, WrapList<Repo>> implements MvpPresenter<RepoMvpView> {
    private final DataManager dataManager;

    @Inject
    public RepoPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void listRepos(final String query, final int page, final boolean pullToRefresh) {
        Timber.d("### get list repos query:%s, page: %d", query, page);
        subscribe(dataManager.getRepos(query, page), pullToRefresh);
    }
}
