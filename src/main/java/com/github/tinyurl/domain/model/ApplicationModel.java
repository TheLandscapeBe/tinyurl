package com.github.tinyurl.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * 请求APP来源
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/02
 */
@Data
public class ApplicationModel {

    @Id
    private Integer id;

    private String appId;

    private String accessKey;

    private String name;

    private String createTime;
}
