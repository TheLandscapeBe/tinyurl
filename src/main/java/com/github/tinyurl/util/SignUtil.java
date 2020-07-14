package com.github.tinyurl.util;

import com.github.tinyurl.constant.Constants;
import com.github.tinyurl.constant.ErrorCode;
import com.github.tinyurl.exception.TinyUrlException;
import org.apache.commons.codec.binary.Hex;

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
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public class SignUtil {

    public static final String SIGN_KEY = "sign";

    public static final String ACCESS_KEY = "key";

    /**
     * 校验签名数据
     * @param request http请求
     * @param key 签名密钥
     * @return 签名是否正确
     */
    public static boolean check(HttpServletRequest request, String key) {
        Enumeration<String> paramKeys = request.getParameterNames();
        if (ObjectUtil.isNull(paramKeys)) {
            return false;
        }

        Map<String, String> treeMap = new TreeMap<>();
        while(paramKeys.hasMoreElements()) {
            String paramKey = paramKeys.nextElement();
            String value = request.getParameter(paramKey);
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            treeMap.put(paramKey, value);
        }

        String requestSign = treeMap.remove(SIGN_KEY);
        String sign = sign(treeMap, key);

        return sign.equals(requestSign);
    }

    /**
     * 签名参数列表
     * @param params 参数表
     * @param key 参数密钥
     * @return 签名字符串
     */
    public static String sign(Map<String, String> params, String key) {
        StringBuilder paramString = new StringBuilder();
        params.forEach((paramKey, value) -> {
            paramString.append(paramKey).append(Constants.EQUALS_MARK).append(value).append(Constants.AMPERSAND);
        });
        paramString.append(ACCESS_KEY).append(Constants.EQUALS_MARK).append(key);

        return HmacUtil.hmacSha256(paramString.toString(), key);
    }
}
