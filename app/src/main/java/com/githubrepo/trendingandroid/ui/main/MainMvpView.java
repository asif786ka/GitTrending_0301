package com.githubrepo.trendingandroid.ui.main;

import com.githubrepo.trendingandroid.data.model.User;
import com.githubrepo.mvp.common.lce.MvpLceView;

public interface MainMvpView extends MvpLceView<User> {
    void unauthorized();
}
