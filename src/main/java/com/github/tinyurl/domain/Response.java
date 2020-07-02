package com.github.tinyurl.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应基类
 *
 * @author jiquanxi
 * @date 2020/07/02
 */
@Data
public class Response<T> implements Serializable {
    private int code;

    private String message;

    private T data;
}
