package com.github.tinyurl.service;

/**
 * Uid返回接口
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/14
 */
public interface UidObject {

    /**
     * 获取Uid字符串
     * @return Uid字符串
     */
    String getStringUid();

    /**
     * 获取Uid长整型
     * @return
     */
    long getLongUid();
}
