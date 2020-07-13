package com.github.tinyurl.util;

import com.github.tinyurl.constant.ErrorCode;
import com.github.tinyurl.exception.TinyUrlException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Hmac 哈希工具类
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/13
 */
public class HmacUtil {

    public static String hmacSha256(String value, String key) {
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA256");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(hexBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new TinyUrlException(ErrorCode.SYSTEM_ERROR, e);
        }
    }
}
