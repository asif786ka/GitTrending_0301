package com.githubrepo.trendingandroid.ui.trending;

import com.githubrepo.trendingandroid.data.DataManager;
import com.githubrepo.trendingandroid.data.model.Repo;
import com.githubrepo.trendingandroid.ui.base.presenter.MvpLceRxPresenter;
import com.githubrepo.mvp.common.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class TrendingPresenter extends MvpLceRxPresenter<TrendingMvpView, List<Repo>> implements MvpPresenter<TrendingMvpView> {
    private final DataManager dataManager;

    @Inject
    public TrendingPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void listTrending(final String language, final String since, final boolean pullToRefresh) {
        Timber.d("### get list repos language:%s, since: %s", language, since);
        subscribe(dataManager.getTrending(language, since), pullToRefresh);
    }
}
