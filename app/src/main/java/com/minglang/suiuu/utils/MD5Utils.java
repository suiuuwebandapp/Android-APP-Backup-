package com.minglang.suiuu.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * <p/>
 * Created by Administrator on 2015/4/24.
 */
public class MD5Utils {

    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        BigInteger hash = new BigInteger(1, md5.digest());
        String str = hash.toString(16);
        while (str.length() < 32) {
            str = "0" + str;
        }
        return str;
    }


}

