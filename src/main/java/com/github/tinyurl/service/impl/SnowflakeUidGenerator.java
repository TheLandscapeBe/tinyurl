package com.github.tinyurl.service.impl;

/**
 * snowflake 有序ID生成器
 * @see http://github.com/twitter/snowflake
 *
 * @author errorfatal89@gmail.com
 */
import com.github.tinyurl.service.UidGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnowflakeUidGenerator implements UidGenerator {

    private static final long EPOCH = 1594720861895L;

    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);
    private static final long WORKER_ID_MASK = -1L ^ (-1L << WORKER_ID_BITS);
    private static final long DATACENTER_ID_MASK = -1L ^ (-1L << DATACENTER_ID_BITS);

    private final long workerId;
    private final long datacenterId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeUidGenerator(final long datacenterId, final long workerId) {
        this.datacenterId = datacenterId & DATACENTER_ID_MASK;
        this.workerId = workerId & WORKER_ID_MASK;
    }

    public synchronized long nextId() {
        long timestamp = timestamp();

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        if (timestamp < lastTimestamp) {
            log.error("Clock is moving backwards. Rejecting requests until " + lastTimestamp + ".");
            throw new IllegalStateException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
        }

        lastTimestamp = timestamp;
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = timestamp();
        }
        return timestamp;
    }

    protected long timestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public String generate() {
        return String.valueOf(nextId());
    }
}
