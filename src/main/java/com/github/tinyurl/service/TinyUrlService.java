package com.github.tinyurl.service;

import com.github.tinyurl.domain.request.GenerateRequest;
import com.github.tinyurl.domain.response.GenerateResponse;

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
     * @return 短连接响应
     */
    GenerateResponse generate(GenerateRequest generateRequest);
}
