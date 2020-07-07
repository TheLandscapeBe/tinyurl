package com.github.tinyurl.dao;

import com.github.tinyurl.domain.model.TinyUrlModel;

/**
 * Url DAO
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
public interface UrlDao {
    /**
     * 插入一条记录
     * @param urlModel
     * @return
     */
    void insert(TinyUrlModel urlModel);

    /**
     * 根据ID查询域名记录
     * @param id 主键ID
     * @return 域名记录
     */
    TinyUrlModel selectById(Long id);
}