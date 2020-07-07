package com.github.tinyurl.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 短网址模型
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/02
 */
@Data
@Entity
@Table(name = "url")
public class TinyUrlModel {
    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 原始URL
     */
    @Column(length = 5000, nullable = false)
    private String orgUrl;

    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createTime;

    /**
     * 过期时间
     */
    @Column
    @Temporal(TemporalType.DATE)
    private Date expireTime;
}
