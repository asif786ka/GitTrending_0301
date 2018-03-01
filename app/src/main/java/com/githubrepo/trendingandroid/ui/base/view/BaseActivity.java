package com.githubrepo.trendingandroid.ui.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.githubrepo.trendingandroid.GitHubTrendingApplication;
import com.githubrepo.trendingandroid.injection.component.ActivityComponent;
import com.githubrepo.trendingandroid.injection.component.ApplicationComponent;
import com.githubrepo.trendingandroid.injection.component.DaggerActivityComponent;
import com.githubrepo.trendingandroid.injection.module.ActivityModule;

import butterknife.ButterKnife;

/**
 * Created by yeungeek on 2016/1/8.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    protected void injectDependencies() {

    }

    public ActivityComponent activityComponent() {
        if (null == mActivityComponent) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(getActivityModule())
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    protected ApplicationComponent getApplicationComponent() {
        return GitHubTrendingApplication.get(this).getComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    private ActivityComponent mActivityComponent;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
