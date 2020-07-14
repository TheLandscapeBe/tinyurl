package com.github.tinyurl.service.impl;

import com.github.tinyurl.service.UidObject;

/**
 * 雪花算法uid对象
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/14
 */
public class SnowflakeUidObject implements UidObject {

    private final long uid;

    public SnowflakeUidObject(final long uid) {
        this.uid = uid;
    }

    @Override
    public String getStringUid() {
        return String.valueOf(uid);
    }

    @Override
    public long getLongUid() {
        return uid;
    }
}
