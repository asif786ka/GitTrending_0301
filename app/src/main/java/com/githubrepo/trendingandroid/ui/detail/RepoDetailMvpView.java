package com.githubrepo.trendingandroid.ui.detail;

import com.githubrepo.mvp.common.lce.MvpLceView;


public interface RepoDetailMvpView extends MvpLceView<String> {
    void starStatus(boolean isStaring);
}
