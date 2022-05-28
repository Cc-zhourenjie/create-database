package com.cc.utils;

/**
 * 处理字符串工具
 *
 * @author cc
 */
public class CcString {

    /**
     * 判断是否相等
     *
     * @param str
     * @param source
     * @return
     */
    public static Boolean equal(String str, String source) {
        if (isEmpty(str) && isNotEmpty(source)) return false;
        if (isNotEmpty(str) && isEmpty(source)) return false;
        return str.equals(source);

    }

    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static Boolean isEmpty(String str) {
        if (str == null) return true;
        if (str.equals("")) return true;
        return false;
    }

    /**
     * 是否不为空
     *
     * @param str
     * @return
     */
    public static Boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


}
