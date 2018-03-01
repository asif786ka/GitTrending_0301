package com.githubrepo.trendingandroid.ui.repos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.githubrepo.trendingandroid.R;
import com.githubrepo.trendingandroid.data.model.Language;
import com.githubrepo.trendingandroid.data.model.Repo;
import com.githubrepo.trendingandroid.ui.detail.DetailActivity;
import com.githubrepo.trendingandroid.util.AppCst;
import com.githubrepo.trendingandroid.views.widget.TriangleLabelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Repo> repos = new ArrayList<>();
    private Language mLanguage;
    private String mTitleStar;

    public RepoAdapter(final Context context, final Language language) {
        mContext = context;
        mLanguage = language;
        mTitleStar = context.getString(R.string.title_star);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repo, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Repo repo = repos.get(position);
        RepoViewHolder repoViewHolder = (RepoViewHolder) holder;
        repoViewHolder.repo = repo;

        if (null == mLanguage || TextUtils.isEmpty(mLanguage.path)) {
            repoViewHolder.repoLanguage.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(repo.getLanguage())) {
                repoViewHolder.repoLanguage.setPrimaryText(repo.getLanguage());
            } else {
                repoViewHolder.repoLanguage.setPrimaryText(AppCst.LANGUAGE_N_A);
            }
        } else {
            repoViewHolder.repoLanguage.setVisibility(View.GONE);
        }

        repoViewHolder.repoName.setText(repo.getFull_name());
        repoViewHolder.repoDesc.setText(repo.getDescription());
        repoViewHolder.repoStars.setText(String.format(mTitleStar, repo.getStargazers_count()));
        if (null != repo.getOwner()) {
            Glide.with(mContext).load(repo.getOwner().getAvatarUrl()).into(repoViewHolder.ownerAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void addAll(final List<Repo> list) {
        repos.addAll(list);
    }

    public void addTopAll(final List<Repo> list) {
        repos.clear();
        repos.addAll(list);
        notifyDataSetChanged();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        Repo repo;

        @Bind(R.id.id_repo_name)
        TextView repoName;
        @Bind(R.id.id_repo_desc)
        TextView repoDesc;
        @Bind(R.id.id_repo_owner_avatar)
        ImageView ownerAvatar;
        @Bind(R.id.id_repo_stars)
        TextView repoStars;
        @Bind(R.id.id_repo_language)
        TriangleLabelView repoLanguage;

        @OnClick(R.id.id_repo_card)
        public void onItemClick() {
            mContext.startActivity(DetailActivity.getStartIntent(mContext, repo));
        }

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
