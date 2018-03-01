package com.githubrepo.trendingandroid.data.model;

public class WrapUser extends User {
    //check if is followed
    private boolean isFollowed;

    public boolean isFollowed() {
        return isFollowed;
    }

    public WrapUser setFollowed(boolean followed) {
        isFollowed = followed;
        return this;
    }
}
