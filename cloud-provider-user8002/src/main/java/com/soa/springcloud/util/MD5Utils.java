package com.soa.springcloud.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Utils {
    public static String MD5(String s) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(s.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
