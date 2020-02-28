package com.sky.base.security.digest;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-21
 */
public final class DigestUtils {

    public static final String md5Hex(byte[] data) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(data);
    }

    public static final String md5Hex(String data) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(data);
    }

    public static final String md5Hex(InputStream data) throws IOException {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(data);
    }

}
