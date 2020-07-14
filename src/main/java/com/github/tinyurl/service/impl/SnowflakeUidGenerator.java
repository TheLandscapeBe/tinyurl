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

    private static final long epoch = 1594720861895L;

    private static final long workerIdBits = 5L;
    private static final long datacenterIdBits = 5L;
    private static final long sequenceBits = 12L;

    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampShift = sequenceBits + workerIdBits + datacenterIdBits;

    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);
    private static final long workerIdMask = -1L ^ (-1L << workerIdBits);
    private static final long datacenterIdMask = -1L ^ (-1L << datacenterIdBits);

    private final long workerId;
    private final long datacenterId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeUidGenerator(final long datacenterId, final long workerId) {
        this.datacenterId = datacenterId & datacenterIdMask;
        this.workerId = workerId & workerIdMask;
    }

    public synchronized long nextId() {
        long timestamp = timestamp();

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                System.out.println("rollover");
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
        return ((timestamp - epoch) << timestampShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
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
