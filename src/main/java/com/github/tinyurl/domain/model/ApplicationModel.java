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
    private String id;

    private String accessKey;

    private String secretKey;

    private String name;

    private String createTime;
}
