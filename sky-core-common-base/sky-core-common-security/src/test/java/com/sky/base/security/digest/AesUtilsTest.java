package com.sky.base.security.digest;

import org.junit.Assert;
import org.junit.Test;

import com.sky.base.security.digest.AesUtils;


/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description
 * @date 2019/3/7
 */
public class AesUtilsTest {

    private static final String KEY = "dqjokej1o2i3123w";

    @Test
    public void encryptToBase64() {
        String encrypt = AesUtils.encryptToBase64("ceshi", KEY);
        Assert.assertEquals(encrypt,"vEIpiGH7E3qNHLPZUJkWtw==");
        Assert.assertNotEquals(encrypt,"1EIpiGH7E3qNHLPZUJkWtw==");
    }

    @Test
    public void decryptFromBase64() {
        String decrypt = AesUtils.decryptFromBase64("vEIpiGH7E3qNHLPZUJkWtw==", KEY);
        Assert.assertEquals(decrypt,"ceshi");
        Assert.assertNotEquals(decrypt,"ceshi1");
    }
}