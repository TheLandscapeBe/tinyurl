package com.github.tinyurl.service;

/**
 * UI生成器
 * @author errorfatal89@gmail.com
 */
public interface UidGenerator {

    /**
     * uid生成
     * @param param
     * @return
     */
    UidObject generate(UidGeneratorParam param);
}
