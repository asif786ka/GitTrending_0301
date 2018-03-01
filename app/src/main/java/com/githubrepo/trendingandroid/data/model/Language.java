package com.githubrepo.trendingandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Language implements Serializable {
    public String name;
    public String path;
    @SerializedName("short_name")
    private String shortName;

    public String getShortName() {
        return shortName == null ? name : shortName;
    }

    public Language() {
    }

    public Language(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Language) {
            return name.equals(((Language) o).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
