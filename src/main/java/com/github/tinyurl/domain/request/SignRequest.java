package com.github.tinyurl.domain.request;

import lombok.Data;

/**
 * 签名数组字段
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
@Data
public class SignRequest {

    /**
     * 签名字符串
     */
    private String sign;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 随机字符串
     */
    private String nonceStr;

    /**
     * 应用ID
     */
    private String appId;

}
