package com.github.tinyurl.constant;

/**
 * 错误编码
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public enum ErrorCode {
    SUCCESS(0, "成功"),
    SYSTEM_ERROR(1, "系统错误"),
    DOMAIN_NOT_EXISTS(100, "指定的域名不存在"),
    RECORD_NOT_EXISTS(101, "记录不存在"),
    TS_EXPIRED(102, "请求时间过长"),
    APP_NOT_EXISTING(103, "应用程序未申请"),
    NONCE_STR_TOO_SHORT(103, "nonceStr长度太短,至少16位"),
    SIGN_NOT_EXISTING(104, "请进行签名（miss sign field）"),
    SIGN_NOT_MATCH(105, "签名错误（sign error）"),
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
