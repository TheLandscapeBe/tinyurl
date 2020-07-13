package com.github.tinyurl.controller.interceptor;

import com.github.tinyurl.config.TinyUrlConfig;
import com.github.tinyurl.constant.ErrorCode;
import com.github.tinyurl.domain.Response;
import com.github.tinyurl.domain.model.ApplicationModel;
import com.github.tinyurl.service.ApplicationService;
import com.github.tinyurl.util.ObjectUtil;
import com.github.tinyurl.util.SignUtil;
import com.github.tinyurl.util.StringUtil;
import com.github.tinyurl.util.WebUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签名验证算法拦截器
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
@Component
public class SignInterceptor implements HandlerInterceptor {

    private static final int NONCE_STR_LENGTH_THRESHOLD = 16;

    @Resource
    private ApplicationService applicationService;

    @Resource
    private TinyUrlConfig tinyUrlConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String timestamp = request.getParameter("timestamp");
        String appId = request.getParameter("appId");
        String nonceStr = request.getParameter("nonceStr");
        String sign = request.getParameter("sign");

        if (StringUtil.isEmpty(timestamp)) {
            long ts = Long.parseLong(timestamp);
            if (System.currentTimeMillis() - ts >= tinyUrlConfig.getTsThresholdMillis()) {
                WebUtil.printJson(response, Response.failure(ErrorCode.TS_EXPIRED));
                return false;
            }
        }
        ApplicationModel applicationModel = null;
        if (StringUtil.isEmpty(appId)) {
            applicationModel = applicationService.selectByAppId(appId);
            if (ObjectUtil.isNull(applicationModel)) {
                WebUtil.printJson(response, Response.failure(ErrorCode.APP_NOT_EXISTING));
                return false;
            }
        }

        if (StringUtil.isEmpty(nonceStr) && nonceStr.length() < NONCE_STR_LENGTH_THRESHOLD) {
            WebUtil.printJson(response, Response.failure(ErrorCode.NONCE_STR_TOO_SHORT));
            return false;
        }

        if (StringUtil.isEmpty(sign)) {
            WebUtil.printJson(response, Response.failure(ErrorCode.SIGN_NOT_EXISTING));
            return false;
        }

        boolean ret = tinyUrlConfig.isCheckSign() ? SignUtil.check(request, applicationModel.getAccessKey()) : true;
        if (!ret) {
            WebUtil.printJson(response, Response.failure(ErrorCode.SIGN_NOT_MATCH));
        }

        return ret;
    }
}
