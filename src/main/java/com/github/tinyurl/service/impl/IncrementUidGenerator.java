package com.github.tinyurl.service.impl;

import com.github.tinyurl.dao.UrlDao;
import com.github.tinyurl.domain.model.UrlModel;
import com.github.tinyurl.domain.request.ShortenRequest;
import com.github.tinyurl.service.UidGenerator;
import com.github.tinyurl.service.UidGeneratorParam;
import com.github.tinyurl.service.UidObject;
import com.github.tinyurl.util.DateUtil;
import com.github.tinyurl.util.Md5Util;
import com.github.tinyurl.util.ObjectUtil;
import com.github.tinyurl.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 自增Uid生成
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/14
 */
@Service("incrementUidGenerator")
public class IncrementUidGenerator implements UidGenerator {

    @Resource
    private UrlDao urlDao;

    @Override
    public UidObject generate(UidGeneratorParam param) {
        ShortenRequest request = (ShortenRequest) param;
        // 校验URL是否已经生成了短连接,不存在则插入
        String hash = Md5Util.encode(request.getUrl(), StringUtil.EMPTY);
        UrlModel urlModel = urlDao.selectByHash(hash);
        if (ObjectUtil.isNull(urlModel)) {
            // 插入记录到数据库
            urlModel = new UrlModel();
            urlModel.setCreateTime(new Date());
            if (StringUtil.isNotEmpty(request.getExpireDate())) {
                urlModel.setExpireTime(DateUtil.parse(request.getExpireDate()));
            }
            urlModel.setOriginUrl(request.getUrl());
            urlModel.setHash(hash);
            // 获取数据库自增ID
            urlDao.insert(urlModel);
        }

        return new IncrementUidObject(urlModel.getId());
    }
}
