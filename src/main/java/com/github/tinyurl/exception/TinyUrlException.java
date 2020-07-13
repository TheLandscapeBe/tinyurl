package com.github.tinyurl.exception;

import com.github.tinyurl.constant.ErrorCode;

/**
 * 短连接服务异常
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public class TinyUrlException extends RuntimeException {

    private ErrorCode errorCode;

    private Exception e;

    public TinyUrlException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public TinyUrlException(ErrorCode errorCode, Exception e) {
        this.errorCode = errorCode;
        this.e = e;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
