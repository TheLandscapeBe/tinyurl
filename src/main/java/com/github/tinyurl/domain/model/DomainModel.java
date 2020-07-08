package com.github.tinyurl.domain.model;

import lombok.Data;

/**
 * 支持的域名列表
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
@Data
public class DomainModel {
    private Integer id;

    private String domain;
}
