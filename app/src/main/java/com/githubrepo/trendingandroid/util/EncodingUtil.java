package com.githubrepo.trendingandroid.util;

import android.util.Base64;

public class EncodingUtil {
    public static final byte[] fromBase64(String content) {
        return Base64.decode(content, Base64.DEFAULT);
    }

    public static final String toBase64(final byte[] content) {
        return Base64.encodeToString(content, Base64.DEFAULT);
    }
}
