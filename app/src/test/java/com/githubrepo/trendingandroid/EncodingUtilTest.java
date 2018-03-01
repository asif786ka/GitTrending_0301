package com.githubrepo.trendingandroid;

import com.githubrepo.trendingandroid.util.EncodingUtil;

import org.junit.Assert;
import org.junit.Test;

public class EncodingUtilTest {
    @Test
    public void testFromBase64() throws Exception {
        String str = "YW5kcm9pZA==";
        Assert.assertEquals(null, EncodingUtil.fromBase64(str));
    }
}
