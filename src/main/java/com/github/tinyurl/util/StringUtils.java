package com.github.tinyurl.util;

/**
 * 字符串工具类
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(String str) {
        return str == null || EMPTY.equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
