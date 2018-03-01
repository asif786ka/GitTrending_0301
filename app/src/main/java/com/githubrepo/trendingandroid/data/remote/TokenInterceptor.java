package com.githubrepo.trendingandroid.data.remote;

import android.content.Context;
import android.text.TextUtils;

import com.githubrepo.trendingandroid.GitHubTrendingApplication;
import com.githubrepo.trendingandroid.data.local.PreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Inject
    PreferencesHelper preferencesHelper;

    public TokenInterceptor(Context context) {
        GitHubTrendingApplication.get(context).getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .method(original.method(), original.body());
        if (null != preferencesHelper && !TextUtils.isEmpty(preferencesHelper.getAccessToken())) {
            requestBuilder.header(GithubApi.AUTH_HEADER, GithubApi.AUTH_TOKEN + preferencesHelper.getAccessToken());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
