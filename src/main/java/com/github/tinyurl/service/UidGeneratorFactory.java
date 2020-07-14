package com.github.tinyurl.service;

/**
 * Uid生成工厂
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/14
 */
public interface UidGeneratorFactory {

    /**
     * 根据类型获取Uid生成器
     * @param type 编解码类型
     * @return Uid编码器
     */
    UidGenerator getUidGenerator(String type);
}
