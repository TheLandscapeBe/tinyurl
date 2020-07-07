package com.github.tinyurl.constant;

/**
 * 错误编码
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
public enum ErrorCode {
    SUCCESS(0, "成功"),
    DOMAIN_NOT_EXISTS(1, "指定的域名不存在"),
    RECORD_NOT_EXISTS(1, "记录不存在"),
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
