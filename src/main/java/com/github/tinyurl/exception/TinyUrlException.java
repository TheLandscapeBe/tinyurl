package com.github.tinyurl.exception;

import com.github.tinyurl.constant.ErrorCode;

/**
 * 短连接服务异常
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public class TinyUrlException extends RuntimeException {

    private final ErrorCode errorCode;

    public TinyUrlException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public TinyUrlException(ErrorCode errorCode, Exception e) {
        super(e);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
