package com.github.tinyurl.dao;

import com.github.tinyurl.domain.model.UrlModel;

/**
 * Url DAO
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public interface UrlDao {
    /**
     * 插入一条记录
     * @param urlModel
     * @return
     */
    void insert(UrlModel urlModel);

    /**
     * 根据ID查询域名记录
     * @param id 主键ID
     * @return 域名记录
     */
    UrlModel selectById(Long id);
}
