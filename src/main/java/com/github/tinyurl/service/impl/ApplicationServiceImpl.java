package com.github.tinyurl.service.impl;

import com.github.tinyurl.dao.ApplicationDao;
import com.github.tinyurl.domain.model.ApplicationModel;
import com.github.tinyurl.service.ApplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 应用服务实现
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/13
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationDao applicationDao;

    @Override
    public ApplicationModel selectByAppId(String appId) {
        return applicationDao.selectByAppId(appId);
    }
}
