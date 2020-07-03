package com.github.tinyurl.domain.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 生成短连接请求
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/02
 */
@Data
public class GenerateRequest {

    /**
     * 原始URL
     */
    @NotEmpty(message = "原始URL不能为空")
    private String url;

    /**
     * 算法，目前支持算法有。。。
     * 默认为：MD5
     */
    private String type = "MD5";

    /**
     * 用户期望可变参数长度，参数最大值需要用户根据实际业务场景评估
     * 场景1：如果用户想转换几个链接，那么可以选择较短的参数长度
     * 场景2：如果用户的短连接无穷大，那么可以不指定参数长度，系统直接生成全局唯一
     */
    private Integer length;

    /**
     * HTTP重定向状态码
     * 用户打开短连接时，需要重定向到原始的URL，该状态码用于告诉浏览器客户端如何重定向
     * 重定向状态码建议使用302，默认为302
     */
    private Integer httpStatusCode = 302;

    /**
     * 应用程序期望使用的域名
     */
    private String domain;
}
