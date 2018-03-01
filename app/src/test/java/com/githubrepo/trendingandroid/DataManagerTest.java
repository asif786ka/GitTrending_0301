package com.githubrepo.trendingandroid;

import com.githubrepo.trendingandroid.data.DataManager;
import com.githubrepo.trendingandroid.data.local.DatabaseHelper;
import com.githubrepo.trendingandroid.data.local.LanguageHelper;
import com.githubrepo.trendingandroid.data.local.PreferencesHelper;
import com.githubrepo.trendingandroid.data.remote.GithubApi;
import com.githubrepo.trendingandroid.data.remote.SimpleApi;
import com.githubrepo.trendingandroid.rxbus.RxBus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;


@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {
    @Mock
    GithubApi mGithubApi;
    @Mock
    SimpleApi mSimpleApi;
    @Mock
    RxBus mRxBus;
    @Mock
    PreferencesHelper mPreferencesHelper;
    @Mock
    DatabaseHelper mDatabaseHelper;
    @Mock
    LanguageHelper mLanguageHelper;

    DataManager mDataManager;
    GitHubTrendingApplication mApplication;

    @Before
    public void setUp() {
        mApplication = Mockito.mock(GitHubTrendingApplication.class);
        mDataManager = new DataManager(mApplication, mGithubApi, mSimpleApi, mRxBus, mPreferencesHelper, mDatabaseHelper, mLanguageHelper);
    }

    @Test
    public void clearTables() {
        Mockito.doReturn(Observable.empty())
                .when(mDatabaseHelper)
                .clearTables();
    }
}
