package com.github.tinyurl.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5哈希工具类
 *
 * @author jiquanxi
 * @date 2020/07/14
 */
public class Md5Util {

    /**
     * md编码
     * @param str 原始字符串
     * @param salt 编码盐值
     * @return 编码后字符串
     */
    public static String encode(String str, String salt) {
        String toBeEncode = str;
        if (StringUtil.isNotEmpty(salt)) {
            toBeEncode += salt;
        }

        return DigestUtils.md5Hex(toBeEncode);
    }
}
