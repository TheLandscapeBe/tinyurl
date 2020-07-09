package com.github.tinyurl.service;

import com.github.tinyurl.domain.request.ShortenRequest;

/**
 * URL生成业务服务
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/03
 */
public interface TinyUrlService {

    /**
     * 生成短连接
     * @param generateRequest 生成请求
     * @return 短连接响应字符串
     */
    String shorten(ShortenRequest generateRequest);

    /**
     * 获取重定向URL
     * @param key 进制编码字符串
     * @return 重定向链接地址
     */
    String getRedirectUrl(String key);
}
