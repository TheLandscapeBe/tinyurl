package com.github.tinyurl.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Hmac加密测试
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/13
 */
@DisplayName("HmacUtil")
public class HmacUtilTest {

    @Test
    public void testHmacSha256() {
        String params = "a1=b1&a2=b2&a3=b3";
        String key = "fghjkladffghj134532";
        String expectHash = "19cf7fff4be53309440a8ebb003cdde94c18c6095768e3f4221b7f7330831906";
        String hash = HmacUtil.hmacSha256(params, key);
        assertEquals(expectHash, hash);
    }
}
