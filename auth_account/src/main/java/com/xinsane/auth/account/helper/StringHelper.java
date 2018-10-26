package com.xinsane.auth.account.helper;

import java.util.Random;

public class StringHelper {
    public static String randomString(int length) {
        String zone = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int zoneLength = zone.length();
        Random random = new Random();
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i<length; i++) {
            int number = random.nextInt(zoneLength);
            tmp.append(zone.charAt(number));
        }
        return tmp.toString();
    }
}
