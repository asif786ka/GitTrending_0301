package com.githubrepo.trendingandroid.injection.component;

import com.githubrepo.trendingandroid.injection.PerActivity;
import com.githubrepo.trendingandroid.injection.module.ActivityModule;
import com.githubrepo.trendingandroid.ui.base.view.BaseToolbarFragment;
import com.githubrepo.trendingandroid.ui.detail.RepoDetailFragment;
import com.githubrepo.trendingandroid.ui.main.MainActivity;
import com.githubrepo.trendingandroid.ui.repos.RepoListFragment;
import com.githubrepo.trendingandroid.ui.trending.TrendingListFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(BaseToolbarFragment baseToolbarFragment);

    void inject(TrendingListFragment trendingListFragment);

    void inject(RepoDetailFragment repoDetailFragment);

    void inject(RepoListFragment repoListFragment);

}
