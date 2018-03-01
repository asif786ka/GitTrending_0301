package com.githubrepo.trendingandroid.data.remote;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface SimpleApi {
    @Headers({
            "Content-Type: text/plain"
    })
    @POST("markdown/raw")
    Observable<String> markdown(@Body String readme);
}
