package com.sky.base.security.digest;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description AES加密工具类
 * @date 2019/3/7
 */
public class AesUtils {

    public static byte[] encrypt(byte[] data, byte[] key) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        } else {
            try {
                SecretKeySpec e = new SecretKeySpec(key, "AES");
                byte[] enCodeFormat = e.getEncoded();
                SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(1, seckey);
                return cipher.doFinal(data);
            } catch (Exception arg6) {
                throw new RuntimeException("encrypt fail!", arg6);
            }
        }
    }

    public static byte[] decrypt(byte[] data, byte[] key) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        } else {
            try {
                SecretKeySpec e = new SecretKeySpec(key, "AES");
                byte[] enCodeFormat = e.getEncoded();
                SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(2, seckey);
                return cipher.doFinal(data);
            } catch (Exception arg6) {
                throw new RuntimeException("decrypt fail!", arg6);
            }
        }
    }

    public static String encryptToBase64(String data, String key) {
        byte[] e = encrypt(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(e);
    }

    public static String decryptFromBase64(String data, String key) {
        byte[] e = Base64.decodeBase64(data);
        byte[] valueByte = decrypt(e, key.getBytes(StandardCharsets.UTF_8));
        return new String(valueByte, StandardCharsets.UTF_8);
    }

    public static String encryptWithKeyBase64(String data, String key) {
        byte[] e = encrypt(data.getBytes(StandardCharsets.UTF_8), Base64.decodeBase64(key.getBytes()));
        return Base64.encodeBase64String(e);
    }

    public static String decryptWithKeyBase64(String data, String key) {
        byte[] e = Base64.decodeBase64(data.getBytes());
        byte[] valueByte = decrypt(e, Base64.decodeBase64(key.getBytes()));
        return new String(valueByte, StandardCharsets.UTF_8);
    }

    public static byte[] genarateRandomKey() {
        KeyGenerator keygen = null;

        try {
            keygen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException arg2) {
            throw new RuntimeException(" genarateRandomKey fail!", arg2);
        }

        SecureRandom random = new SecureRandom();
        keygen.init(random);
        SecretKey key = keygen.generateKey();
        return key.getEncoded();
    }

    public static String genarateRandomKeyWithBase64() {
        return Base64.encodeBase64String(genarateRandomKey());
    }

}
