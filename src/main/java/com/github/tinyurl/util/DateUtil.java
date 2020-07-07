package com.github.tinyurl.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
public class DateUtil {

    public static Date parse(String str) {
        LocalDateTime localDateTime = LocalDateTime.parse(str);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}