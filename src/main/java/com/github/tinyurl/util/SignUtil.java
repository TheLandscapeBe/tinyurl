package com.github.tinyurl.util;

import com.github.tinyurl.constant.Constants;
import com.github.tinyurl.constant.ErrorCode;
import com.github.tinyurl.exception.TinyUrlException;
import com.sun.crypto.provider.HmacSHA1;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名工具类
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
public class SignUtil {

    public static final String SIGN_KEY = "sign";

    public static final String ACCESS_KEY = "key";

    public static boolean check(HttpServletRequest request, String key) {
        Enumeration<String> paramKeys = request.getParameterNames();
        if (ObjectUtil.isNull(paramKeys)) {
            return false;
        }

        Map<String, String> treeMap = new TreeMap<>();
        while(paramKeys.hasMoreElements()) {
            String paramKey = paramKeys.nextElement();
            String value = request.getParameter(paramKey);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            treeMap.put(paramKey, value);
        }

        String requestSign = treeMap.remove(SIGN_KEY);

        StringBuilder paramString = new StringBuilder();
        treeMap.forEach((paramKey, value) -> {
            paramString.append(paramKey).append(Constants.AMPERSAND);
        });
        paramString.append(ACCESS_KEY).append(Constants.AMPERSAND).append(key);

        String sign = hmacSha256(paramString.toString(), key);
        return sign.equals(requestSign);
    }



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
