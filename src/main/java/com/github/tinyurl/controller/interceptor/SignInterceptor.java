package com.github.tinyurl.controller.interceptor;

import com.github.tinyurl.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签名验证算法拦截器
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
@Component
public class SignInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String timestamp = request.getParameter("timestamp");
        String appId = request.getParameter("appId");
        String nonceStr = request.getParameter("nonceStr");
        String sign = request.getParameter("sign");

        if (StringUtils.isEmpty(timestamp)) {

        }

        if (StringUtils.isEmpty(appId)) {

        }

        if (StringUtils.isEmpty(nonceStr)) {

        }

        if (StringUtils.isEmpty(sign)) {

        }



        return false;
    }
}
