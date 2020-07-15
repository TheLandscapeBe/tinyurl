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
     * 插入数据库
     * @param urlModel url模型
     */
    void insertWithId(UrlModel urlModel);

    /**
     * 根据ID查询URL记录
     * @param id 主键ID
     * @return 域名记录
     */
    UrlModel selectById(Long id);

    /**
     * 根据哈希值查询URL记录
     * @param hash
     * @return
     */
    UrlModel selectByHash(String hash);
}
