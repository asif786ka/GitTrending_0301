package com.githubrepo.trendingandroid.data.remote;

import android.content.Context;

import com.githubrepo.trendingandroid.GitHubTrendingApplication;
import com.githubrepo.trendingandroid.rxbus.RxBus;
import com.githubrepo.trendingandroid.rxbus.event.BusEvent;
import com.githubrepo.trendingandroid.util.HttpStatus;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

public class UnauthorisedInterceptor implements Interceptor {
    @Inject
    RxBus rxBus;

    public UnauthorisedInterceptor(Context context) {
        GitHubTrendingApplication.get(context).getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == HttpStatus.HTTP_UNAUTHORIZED) {
            Timber.e("### unauthorized");
            rxBus.send(new BusEvent.AuthenticationError());
        }
        return response;
    }
}
