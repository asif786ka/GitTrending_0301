package com.githubrepo.trendingandroid.ui;

import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;
import com.githubrepo.trendingandroid.R;
import com.githubrepo.trendingandroid.ui.main.MainActivity;
import com.githubrepo.trendingandroid.util.AppCst;

public class LauncherActivity extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {
        //Title
        configSplash.setTitleSplash(getString(R.string.title_app));
        configSplash.setBackgroundColor(R.color.colorPrimary);
        configSplash.setTitleTextSize(30f);

        //path
        configSplash.setPathSplash(AppCst.GITHUB); //set path String
        configSplash.setOriginalHeight(512); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(512); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(2000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorAccent); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(2500);
        configSplash.setPathSplashFillColor(R.color.blue_200); //path object filling color
    }

    @Override
    public void animationsFinished() {
        startActivity(MainActivity.getStartIntent(this));
        finish();
    }
}
