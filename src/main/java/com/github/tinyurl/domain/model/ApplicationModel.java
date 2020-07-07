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

/**
 * 请求APP来源
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/02
 */
@Data
@Entity
@Table(name = "application")
public class ApplicationModel {

    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false)
    private String accessKey;

    @Column(length = 256, nullable = false)
    private String secretKey;

    @Column(length = 256, nullable = false)
    private String name;

    @Column(length = 256, nullable = false)
    private String domain;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private String createTime;
}
