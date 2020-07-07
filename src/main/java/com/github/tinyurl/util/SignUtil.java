package com.github.tinyurl.util;

import com.github.tinyurl.constant.Constants;

import javax.servlet.http.HttpServletRequest;
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

    public static boolean verifySign(HttpServletRequest request, String accessKey) {
        Enumeration<String> paramKeys = request.getParameterNames();
        if (ObjectUtil.isNull(paramKeys)) {
            return false;
        }

        Map<String, String> treeMap = new TreeMap<>();
        while(paramKeys.hasMoreElements()) {
            String key = paramKeys.nextElement();
            String value = request.getParameter(key);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            treeMap.put(key, value);
        }

        treeMap.remove(SIGN_KEY);

        StringBuilder paramString = new StringBuilder();
        treeMap.forEach((key, value) -> {
            paramString.append(key).append(Constants.AMPERSAND);
        });
        paramString.append(ACCESS_KEY).append(Constants.AMPERSAND).append(accessKey);
    }
}
