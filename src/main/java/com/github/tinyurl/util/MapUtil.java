package com.github.tinyurl.util;

import com.github.tinyurl.service.UidGenerator;

import java.util.Map;

/**
 * Map工具类
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public class MapUtil {

    private MapUtil() {}

    public static boolean isNotEmpty(Map map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }
}
