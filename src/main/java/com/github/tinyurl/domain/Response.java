package com.github.tinyurl.domain;

import com.github.tinyurl.constant.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应基类
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/02
 */
@Data
public class Response<T> implements Serializable {
    private int code;

    private String message;

    private T data;

    public Response() {
    }

    public Response(int code, String message) {
        this(code, message, null);
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response success() {
        return new Response(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());
    }

    public static Response success(Object data) {
        return new Response(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static Response failure(ErrorCode errorCode) {
        return new Response(errorCode.getCode(), errorCode.getMessage());
    }
}
