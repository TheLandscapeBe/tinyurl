package com.github.tinyurl.controller.filter;

import com.alibaba.fastjson.JSON;
import com.github.tinyurl.config.TinyUrlConfig;
import com.github.tinyurl.constant.ErrorCode;
import com.github.tinyurl.domain.Response;
import com.github.tinyurl.domain.model.ApplicationModel;
import com.github.tinyurl.service.ApplicationService;
import com.github.tinyurl.util.ObjectUtil;
import com.github.tinyurl.util.SignUtil;
import com.github.tinyurl.util.StringUtil;
import com.github.tinyurl.util.WebUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名验证算法拦截器
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
@Component
@Order(1)
public class SignFilter implements Filter {

    private static final int NONCE_STR_LENGTH_THRESHOLD = 16;

    @Resource
    private ApplicationService applicationService;

    @Resource
    private TinyUrlConfig tinyUrlConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(
                (HttpServletRequest) servletRequest);
        if (!tinyUrlConfig.isCheckSign()) {
            chain.doFilter(wrappedRequest, servletResponse);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 处理Restful规范
        Map<String, String> parameterMap = new HashMap<>();
        String method = request.getMethod();
        if (method.equalsIgnoreCase(HttpMethod.POST.name()) || method.equalsIgnoreCase(HttpMethod.PUT.name())) {
            String body = IOUtils.toString(wrappedRequest.getReader());
            parameterMap = JSON.parseObject(body, Map.class);
        } else if (method.equals(HttpMethod.GET)) {
            Enumeration<String> paramKeys = servletRequest.getParameterNames();
            if (ObjectUtil.isNull(paramKeys)) {
                WebUtil.printJson(response, Response.failure(ErrorCode.BAD_PARAMETER));
                return;
            }

            parameterMap = new HashMap<>();
            while(paramKeys.hasMoreElements()) {
                String paramKey = paramKeys.nextElement();
                String value = servletRequest.getParameter(paramKey);
                if (StringUtil.isEmpty(value)) {
                    continue;
                }
                parameterMap.put(paramKey, value);
            }
        } else {
            chain.doFilter(wrappedRequest, servletResponse);
        }

        String timestamp = parameterMap.get("timestamp");
        String appId = parameterMap.get("appId");
        String nonceStr = parameterMap.get("nonceStr");
        String sign = parameterMap.get("sign");

        if (StringUtil.isEmpty(timestamp)) {
            long ts = Long.parseLong(timestamp);
            if (System.currentTimeMillis() - ts >= tinyUrlConfig.getTsThresholdMillis()) {
                WebUtil.printJson(response, Response.failure(ErrorCode.TS_EXPIRED));
                return;
            }
        }
        if (StringUtil.isEmpty(appId)) {
            WebUtil.printJson(response, Response.failure(ErrorCode.APP_NOT_EXISTING));
            return;
        }
        ApplicationModel applicationModel = applicationService.selectByAppId(appId);
        if (ObjectUtil.isNull(applicationModel)) {
            WebUtil.printJson(response, Response.failure(ErrorCode.APP_NOT_EXISTING));
            return;
        }

        if (StringUtil.isEmpty(nonceStr) && nonceStr.length() < NONCE_STR_LENGTH_THRESHOLD) {
            WebUtil.printJson(response, Response.failure(ErrorCode.NONCE_STR_TOO_SHORT));
            return;
        }

        if (StringUtil.isEmpty(sign)) {
            WebUtil.printJson(response, Response.failure(ErrorCode.SIGN_NOT_EXISTING));
            return;
        }

        boolean ret = SignUtil.check(parameterMap, applicationModel.getAccessKey());
        if (!ret) {
            WebUtil.printJson(response, Response.failure(ErrorCode.SIGN_NOT_MATCH));
            return;
        }

        wrappedRequest.resetInputStream();
        chain.doFilter(wrappedRequest, servletResponse);
    }

    private static class ResettableStreamHttpServletRequest extends
            HttpServletRequestWrapper {

        private byte[] rawData;
        private HttpServletRequest request;
        private ResettableServletInputStream servletStream;

        public ResettableStreamHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
            this.servletStream = new ResettableServletInputStream();
        }


        public void resetInputStream() {
            servletStream.stream = new ByteArrayInputStream(rawData);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getReader(), StandardCharsets.UTF_8);
                servletStream.stream = new ByteArrayInputStream(rawData);
            }
            return servletStream;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getReader(), StandardCharsets.UTF_8);
                servletStream.stream = new ByteArrayInputStream(rawData);
            }
            return new BufferedReader(new InputStreamReader(servletStream));
        }


        private class ResettableServletInputStream extends ServletInputStream {

            private InputStream stream;

            @Override
            public int read() throws IOException {
                return stream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        }
    }
}
