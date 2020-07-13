package com.github.tinyurl.service;

import com.github.tinyurl.domain.model.ApplicationModel;

/**
 * 应用服务
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/13
 */
public interface ApplicationService {

    /**
     * 根据AppId查询应用信息
     * @param appId appid
     * @return app信息
     */
    ApplicationModel selectByAppId(String appId);
}
