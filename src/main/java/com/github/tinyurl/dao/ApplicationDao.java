package com.github.tinyurl.dao;

import com.github.tinyurl.domain.model.ApplicationModel;

/**
 * Application DAO
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public interface ApplicationDao {

    /**
     * 根据应用ID查询应用
     * @param appId 应用ID
     * @return 应用对象
     */
    ApplicationModel selectByAppId(String appId);
}
