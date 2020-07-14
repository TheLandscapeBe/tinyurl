package com.github.tinyurl.service.impl;

import com.github.tinyurl.config.TinyUrlConfig;
import com.github.tinyurl.constant.Constants;
import com.github.tinyurl.constant.ErrorCode;
import com.github.tinyurl.dao.DomainDao;
import com.github.tinyurl.dao.UrlDao;
import com.github.tinyurl.domain.model.UrlModel;
import com.github.tinyurl.domain.request.ShortenRequest;
import com.github.tinyurl.exception.TinyUrlException;
import com.github.tinyurl.service.TinyUrlService;
import com.github.tinyurl.service.UidGenerator;
import com.github.tinyurl.service.UidGeneratorFactory;
import com.github.tinyurl.service.UidGeneratorParam;
import com.github.tinyurl.service.UidObject;
import com.github.tinyurl.util.DateUtil;
import com.github.tinyurl.util.Md5Util;
import com.github.tinyurl.util.ObjectUtil;
import com.github.tinyurl.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 短连接生成业务服务实现
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/03
 */
@Service
public class TinyUrlServiceImpl implements TinyUrlService {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ALPHABET_LENGTH = ALPHABET.length();

    @Resource
    private UrlDao urlDao;

    @Resource
    private DomainDao domainDao;

    @Resource
    private TinyUrlConfig tinyUrlConfig;

    @Resource
    private UidGeneratorFactory uidGeneratorFactory;


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String shorten(ShortenRequest request) {
        // 检查是否存在该domain
        Integer domainId = domainDao.selectByDomain(request.getDomain());
        if (domainId == null) {
            throw new TinyUrlException(ErrorCode.DOMAIN_NOT_EXISTS);
        }

        long uid = 0L;
        String hash = Md5Util.encode(request.getUrl(), StringUtil.EMPTY);
        UrlModel urlModel = urlDao.selectByHash(hash);
        if (ObjectUtil.isNotNull(urlModel)) {
            uid = urlModel.getId();
        } else {
            UidGenerator uidGenerator = uidGeneratorFactory.getUidGenerator(tinyUrlConfig.getUidGenType());
            if (ObjectUtil.isNull(uidGenerator)) {
                throw new TinyUrlException(ErrorCode.UID_GEN_TYPE_NOT_EXISTING);
            }

            UidObject uidObject = uidGenerator.generate(request);
            uid = uidObject.getLongUid();
        }

        // 校验是否重复

        // 通过ID计算进制字符串
        StringBuilder finalUrl = new StringBuilder();
        finalUrl.append(Constants.HTTP_SCHEMA)
                .append(request.getDomain())
                .append(Constants.HTTP_SLASH)
                .append(encode(uid));
        return finalUrl.toString();
    }

    @Override
    public String getRedirectUrl(String key) {
        long number = decode(key);
        UrlModel tinyUrlModel = urlDao.selectById(number);
        if (ObjectUtil.isNull(tinyUrlModel)) {
            throw new TinyUrlException(ErrorCode.RECORD_NOT_EXISTS);
        }

        return tinyUrlModel.getOriginUrl();
    }

    /**
     * 将数字编码为进制字符串
     * @param number 链接数字编码
     * @return 短连接字符串
     */
    private static String encode(long number) {
        StringBuilder chip = new StringBuilder(8);
        while (number > 0) {
            chip.append(ALPHABET.charAt((int)(number % ALPHABET_LENGTH)));
            number /= ALPHABET_LENGTH;
        }

        return chip.reverse().toString();
    }

    /**
     * 将进制编码转换为数字
     * @param key 进制编码
     * @return 数字
     */
    private static long decode(String key) {
        long number = 0L;
        for (int i = 0; i < key.length(); i++) {
            long pow = pow(key, i);
            number += pow * ALPHABET.indexOf(key.charAt(i));
        }

        return number;
    }

    /**
     * 求幂
     * @param key 进制编码字符串
     * @param i 索引
     * @return 数字编码
     */
    private static long pow(String key, int i) {
        long pow = 1L;
        for (int j = 0; j < key.length() - i - 1; j++) {
            pow *= ALPHABET_LENGTH;
        }
        return pow;
    }
}
