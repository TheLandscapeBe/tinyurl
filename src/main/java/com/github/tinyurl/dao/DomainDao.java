package com.github.tinyurl.dao;

/**
 * 域名dao
 *
 * @author jiquanxi
 * @date 2020/07/07
 */
public interface DomainDao {
    /**
     * 根据域名查询域名ID
     * @param domain 域名字符串
     * @return 域名ID
     */
    Integer selectByDomain(String domain);
}
