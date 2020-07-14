package com.github.tinyurl.service.impl;

import com.github.tinyurl.service.UidGenerator;
import com.github.tinyurl.service.UidGeneratorFactory;
import com.github.tinyurl.util.MapUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Uid生成工厂实现
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/14
 */
@Service
public class UidGeneratorFactoryImpl implements UidGeneratorFactory {

    private static final String UID_INCREMENT_KEY = "increment";

    private static final String UID_SNOWFLAKE_KEY = "snowflake";

    @Resource
    private IncrementUidGenerator incrementUidGenerator;

    @Resource
    private SnowflakeUidGenerator snowflakeUidGenerator;

    private volatile Map<String, UidGenerator> generatorTable = new HashMap<>(2);

    public UidGeneratorFactoryImpl() {

    }

    @Override
    public UidGenerator getUidGenerator(String type) {
        synchronized (this) {
            if (MapUtil.isEmpty(generatorTable)) {
                generatorTable.put(UID_INCREMENT_KEY, incrementUidGenerator);
                generatorTable.put(UID_SNOWFLAKE_KEY, snowflakeUidGenerator);
            }
        }
        return generatorTable.get(type);
    }
}
