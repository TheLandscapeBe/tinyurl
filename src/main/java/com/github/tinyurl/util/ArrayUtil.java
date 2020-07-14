package com.github.tinyurl.util;

/**
 * 数组工具类
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public class ArrayUtil {
    private ArrayUtil() {}
    public static boolean isNotEmpty(Object[] arr) {
        return arr != null && arr.length > 0;
    }
}
