package com.githubrepo.trendingandroid.data.model;

import com.google.gson.annotations.SerializedName;


public class AccessTokenResp {
    @SerializedName(value = "access_token")
    private String accessToken;
    private String scope;
    @SerializedName(value = "token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public AccessTokenResp setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public AccessTokenResp setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AccessTokenResp setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }
}
