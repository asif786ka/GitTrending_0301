package com.githubrepo.trendingandroid.ui.repos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.githubrepo.trendingandroid.R;
import com.githubrepo.trendingandroid.data.model.Language;
import com.githubrepo.trendingandroid.data.model.Repo;
import com.githubrepo.trendingandroid.data.model.WrapList;
import com.githubrepo.trendingandroid.ui.base.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.githubrepo.trendingandroid.ui.base.view.BaseLceActivity;
import com.githubrepo.trendingandroid.ui.base.view.BasePageFragment;
import com.githubrepo.trendingandroid.util.AppCst;

import javax.inject.Inject;

public class RepoListFragment extends BasePageFragment<WrapList<Repo>, RepoMvpView, RepoPresenter> implements RepoMvpView {
    @Inject
    RepoPresenter repoPresenter;

    Language language;
    private RepoAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAdapter;

    public static Fragment newInstance(Context context, Language language) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCst.EXTRA_LANGUAGE, language);
        return Fragment.instantiate(context, RepoListFragment.class.getName(), bundle);
    }

    @Override
    public RepoPresenter createPresenter() {
        return repoPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = (Language) getArguments().getSerializable(AppCst.EXTRA_LANGUAGE);
    }

    @Override
    protected void initAdapter() {
        adapter = new RepoAdapter(getActivity(), language);

        mHeaderAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mHeaderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getString(R.string.error_repositories) + "\n" + e.getMessage();
    }

    @Override
    public void setData(WrapList<Repo> data) {
        mCount = data.getTotal_count();
        if (!mLoadMore) {
            adapter.addTopAll(data.getItems());
            mCurrentSize = data.getItems().size();
        } else {
            adapter.addAll(data.getItems());
            mCurrentSize += data.getItems().size();
        }

        checkViewState();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().listRepos(AppCst.LANGUAGE_PREFIX + language.path, mPage, pullToRefresh);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        if (getActivity() instanceof BaseLceActivity) {
            ((BaseLceActivity) getActivity()).activityComponent().inject(this);
        }
    }
}
