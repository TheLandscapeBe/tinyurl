package com.github.tinyurl.domain.model;

import lombok.Data;

import java.util.Date;

/**
 * 短网址模型
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/02
 */
@Data
public class UrlModel {

    private Long id;

    /**
     * 原始URL
     */
    private String originUrl;

    /**
     * 原始链接哈希值
     */
    private String hash;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 过期时间
     */
    private Date expireTime;
}
