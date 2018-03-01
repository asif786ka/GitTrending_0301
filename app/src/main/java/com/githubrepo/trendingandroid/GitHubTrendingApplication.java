package com.githubrepo.trendingandroid;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.githubrepo.trendingandroid.injection.component.ApplicationComponent;
import com.githubrepo.trendingandroid.injection.component.DaggerApplicationComponent;
import com.githubrepo.trendingandroid.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by yeungeek on 2016/1/8.
 */
public class GitHubTrendingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initComponent();
        initStetho();
        initUpgrade();
    }

    private void initComponent() {
        if (BuildConfig.DEBUG) {
            Timber.d("dagger inject");
        }
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initUpgrade() {
        Beta.autoInit = false;
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 60 * 1000;

        Bugly.init(this, BuildConfig.BUGLY_APPID, false);
    }

    public static GitHubTrendingApplication get(final Context context) {
        return (GitHubTrendingApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    ApplicationComponent mApplicationComponent;
}
