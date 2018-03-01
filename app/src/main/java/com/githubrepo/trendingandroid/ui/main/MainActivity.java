package com.githubrepo.trendingandroid.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.githubrepo.trendingandroid.R;
import com.githubrepo.trendingandroid.data.model.User;
import com.githubrepo.trendingandroid.ui.base.view.BaseLceActivity;
import com.githubrepo.trendingandroid.ui.trending.TrendingFragment;

import javax.inject.Inject;

import butterknife.Bind;
import timber.log.Timber;

public class MainActivity extends BaseLceActivity<View, User, MainMvpView, MainPresenter> implements MainMvpView {
    //@Bind(R.id.draw_layout)
    //DrawerLayout drawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Nullable
    @Bind(R.id.id_drawer_header_avatar)
    ImageView mAvatarView;
    @Nullable
    @Bind(R.id.id_drawer_header_name)
    TextView mNameView;
    @Nullable
    @Bind(R.id.id_drawer_header_email)
    TextView mEmailView;

    @Inject
    MainPresenter mainPresenter;

    private TrendingFragment trendingFragment;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init upgrade
        Beta.init(getApplicationContext(), false);

        /*if (null != navigationView) {
            setupDrawerContent(navigationView);
            //default
            navigationView.setCheckedItem(R.id.menu_users);
            View headerView = navigationView.getHeaderView(0);
            mAvatarView = ButterKnife.findById(headerView, R.id.id_drawer_header_avatar);
            mNameView = ButterKnife.findById(headerView, R.id.id_drawer_header_name);
            mEmailView = ButterKnife.findById(headerView, R.id.id_drawer_header_email);

            mAvatarView.setOnClickListener(this);
            mNameView.setOnClickListener(this);
        }*/

        mainPresenter.checkUserStatus();
        //selectFragment(R.id.menu_users);
        invokeTrending();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        activityComponent().inject(this);
    }

    /*private void updateUser(User data) {
        if (null == data) {
            mIsSignin = false;
            mNameView.setText(null);
            mEmailView.setText(null);
            Glide.with(this).load(R.drawable.ic_avatar).into(mAvatarView);
            return;
        }

        Timber.d("### login success user info: %s", data.getLogin());

        mIsSignin = true;

        Glide.with(this).load(data.getAvatar_url() + ImageSize.AVATAR_120).into(mAvatarView);

        mNameView.setText(data.getLogin());
        mEmailView.setText(data.getEmail());
    }*/

    /*private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                selectFragment(item.getItemId());
                return true;
            }
        });
    }*/


    public void invokeTrending() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (null == trendingFragment) {
            trendingFragment = new TrendingFragment();
            transaction.add(R.id.id_main_frame_container, trendingFragment, "trending");
        } else {
            transaction.show(trendingFragment);
        }
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                //drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
        // drawerLayout.closeDrawer(Gravity.LEFT);
        //} else {
        super.onBackPressed();
        //}
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return mainPresenter;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        Timber.d("### login start");
    }

    @Override
    public void showContent() {
        Timber.d("### login success");
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        Timber.e(e, "### login error");
    }

    @Override
    public void setData(User data) {
        //updateUser(data);
    }

    @Override
    public void unauthorized() {
    }
}
