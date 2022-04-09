package com.cc.utils;

public class CcString {


    public static Boolean equal(String str, String source) {
        if (isEmpty(str) && isNotEmpty(source)) return false;
        if (isNotEmpty(str) && isEmpty(source)) return false;
        return str.equals(source);

    }


    public static Boolean isEmpty(String str) {
        if (str == null) return true;
        if (str.equals("")) return true;
        return false;
    }


    public static Boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


}
