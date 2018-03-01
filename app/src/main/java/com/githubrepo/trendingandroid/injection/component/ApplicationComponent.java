package com.githubrepo.trendingandroid.injection.component;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.githubrepo.trendingandroid.GitHubTrendingApplication;
import com.githubrepo.trendingandroid.data.DataManager;
import com.githubrepo.trendingandroid.data.local.DatabaseHelper;
import com.githubrepo.trendingandroid.data.local.PreferencesHelper;
import com.githubrepo.trendingandroid.data.remote.GithubApi;
import com.githubrepo.trendingandroid.data.remote.SimpleApi;
import com.githubrepo.trendingandroid.data.remote.TokenInterceptor;
import com.githubrepo.trendingandroid.data.remote.UnauthorisedInterceptor;
import com.githubrepo.trendingandroid.injection.ApplicationContext;
import com.githubrepo.trendingandroid.injection.module.ApplicationModule;
import com.githubrepo.trendingandroid.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(GitHubTrendingApplication gitHubTrendingApplication);
    void inject(UnauthorisedInterceptor unauthorisedInterceptor);
    void inject(TokenInterceptor tokenInterceptor);

    //Exposed to sub-graphs.
    @ApplicationContext
    Context context();

    Application application();

    GithubApi githubApi();
    SimpleApi simpleApi();

    DatabaseHelper databaseHelper();

    PreferencesHelper preferencesHelper();

    DataManager dataManager();

    RxBus rxBus();

    Gson gson();
}
