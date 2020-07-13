package com.github.tinyurl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 服务配置
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/13
 */
@Data
@Component
@ConfigurationProperties("tinyurl")
public class TinyUrlConfig {

    /**
     * 是否校验签名
     */
    private boolean checkSign;

    /**
     * 签名时间戳失效最大值(毫秒)
     */
    private long tsThresholdMillis;
}
