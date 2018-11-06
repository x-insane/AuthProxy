package com.xinsane.auth.account.helper;

import java.util.Random;

public class StringHelper {
    private static Random random = new Random();

    /**
     * 返回指定长度字母和数字组成的随机字符串
     * @param length 随机字符串长度
     * @return 指定长度字母和数字组成的随机字符串
     */
    public static String randomString(int length) {
        String zone = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int zoneLength = zone.length();
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i<length; i++) {
            int number = random.nextInt(zoneLength);
            tmp.append(zone.charAt(number));
        }
        return tmp.toString();
    }

    /**
     * 返回随机6位数字 Code
     * @return Code（6位数字）
     */
    public static String randomNumberCode6() {
        return String.valueOf(random.nextInt(900000) + 100000);
    }
}
