package com.github.tinyurl.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 短网址模型
 *
 * @author jiquanxi
 * @date 2020/07/02
 */
@Data
public class TinyUrlModel {
    @Id
    private String id;

    /**
     * 原始URL
     */
    private String orgUrl;

    /**
     * 短网址
     */
    private String tinyUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 应用ID
     */
    private String appId;
}
