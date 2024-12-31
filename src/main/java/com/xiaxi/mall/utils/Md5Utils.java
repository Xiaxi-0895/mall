package com.xiaxi.mall.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Utils {
    public static String getMd5(String str) {return DigestUtils.md5Hex(str);}
    public static String getMd5(String str,int salt) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] * salt);
        }
        return DigestUtils.md5Hex(new String(chars));
    }
}
