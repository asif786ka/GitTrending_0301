package com.githubrepo.trendingandroid.ui.base.adapter;

import android.view.View;

public interface OnListLoadNextPageListener {

    /**
     * 开始加载下一页
     *
     * @param view 当前RecyclerView/ListView/GridView
     */
    public void onLoadNextPage(View view);
}
