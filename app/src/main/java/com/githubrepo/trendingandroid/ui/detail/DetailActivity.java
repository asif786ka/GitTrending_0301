package com.githubrepo.trendingandroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.githubrepo.trendingandroid.R;
import com.githubrepo.trendingandroid.data.model.Repo;
import com.githubrepo.trendingandroid.ui.base.view.BaseActivity;

import java.io.Serializable;


public class DetailActivity extends BaseActivity {
    private static final String EXTRA_DETAIL = "EXTRA_DETAIL";

    private Serializable serializable;

    public static Intent getStartIntent(Context context, Serializable serializable) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_DETAIL, serializable);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        serializable = getIntent().getSerializableExtra(EXTRA_DETAIL);
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (null != serializable) {
            if (serializable instanceof Repo) {
                transaction.replace(R.id.id_detail, RepoDetailFragment.newInstance(this, (Repo) serializable));
            }
        }

        transaction.commit();
    }
}
