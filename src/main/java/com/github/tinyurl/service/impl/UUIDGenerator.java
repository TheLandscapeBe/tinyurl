package com.github.tinyurl.service.impl;

import com.github.tinyurl.service.UidGenerator;
import com.github.tinyurl.service.UidGeneratorParam;
import com.github.tinyurl.service.UidObject;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Java UUID生成器
 * @author errorfatal89@gmail.com
 */
@Service("UUIDGenerator")
public class UUIDGenerator implements UidGenerator {

    @Override
    public UidObject generate(UidGeneratorParam param) {
        throw new UnsupportedOperationException();
    }
}
