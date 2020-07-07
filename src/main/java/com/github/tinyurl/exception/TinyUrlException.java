package com.github.tinyurl.exception;

import com.github.tinyurl.constant.ErrorCode;

/**
 * 短连接服务异常
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
public class TinyUrlException extends RuntimeException {

    private ErrorCode errorCode;

    public TinyUrlException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
