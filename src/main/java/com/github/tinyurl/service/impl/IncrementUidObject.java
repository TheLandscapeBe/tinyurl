package com.github.tinyurl.service.impl;

import com.github.tinyurl.service.UidObject;

/**
 * 自增Uid对象
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/14
 */
public class IncrementUidObject implements UidObject {

    private final long uid;

    public IncrementUidObject(final long uid) {
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
