package com.github.tinyurl.controller;

import com.github.tinyurl.domain.Response;
import com.github.tinyurl.domain.request.GenerateRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * 短连接生成Controller
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/02
 */
@Controller("/")
public class TinyUrlController {

    @Resource
    private HttpServletRequest request;

    /**
     * 生成短连接
     * @param generateRequest 生成短连接请求
     * @return 短连接
     */
    @GetMapping("/generate")
    public Response generate(@Valid GenerateRequest generateRequest) {

        return new Response();
    }

    /**
     * 重定向到原始URL
     * @param key 短连接参数
     */
    @GetMapping("/{key}")
    public void redirect(@PathVariable("key") String key) {
        Map<String, String[]> parameterMap =request.getParameterMap();

        // 处理短连接中携带参数场景
        if (parameterMap != null && !parameterMap.isEmpty()) {
            // todo 原始URL，需要从数据库获取
            String orgUrl = "";
            if (orgUrl != null && !"".equals(orgUrl)) {

            }
        }
    }
}
