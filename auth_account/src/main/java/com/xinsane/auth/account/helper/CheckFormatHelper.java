package com.xinsane.auth.account.helper;

import java.util.regex.Pattern;

public class CheckFormatHelper {
    public static boolean checkUsername(String string) {
        return regMatch("^[-a-zA-Z0-9_\\u4e00-\\u9fa5]+$", string);
    }

    public static boolean checkEmail(String string) {
        return regMatch("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", string);
    }

    public static boolean checkPhone(String string) {
        return regMatch("^1[34578]\\d{9}$", string);
    }

    private static boolean regMatch(String reg, String text) {
        if (text == null)
            return false;
        return Pattern.compile(reg).matcher(text).matches();
    }
}
