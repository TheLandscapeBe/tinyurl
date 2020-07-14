package com.github.tinyurl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 雪花算法配置
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/14
 */
@Data
@Component
@ConfigurationProperties("tinyurl.snowflake")
public class SnowflakeConfig {

    private long dataCenterId;

    private long workerId;
}
