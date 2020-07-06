package com.github.tinyurl.service.impl;

import com.github.tinyurl.domain.request.GenerateRequest;
import com.github.tinyurl.domain.response.GenerateResponse;
import com.github.tinyurl.service.TinyUrlService;

/**
 * 短连接生成业务服务实现
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/03
 */
public class TinyUrlServiceImpl implements TinyUrlService {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ALPHABET_LENGTH = ALPHABET.length();

    @Override
    public GenerateResponse generate(GenerateRequest generateRequest) {
        // 插入记录到数据库
        // 获取数据库自增ID
        // 通过ID计算进制字符串

        StringBuilder sb = new StringBuilder();
        // TODO 简单模拟
        encode(1L);

        // 将获取到的key回写到数据库

        // 组装响应内容返回
        return null;
    }

    private static String encode(long number) {
        StringBuilder chip = new StringBuilder(8);
        while (number > 0) {
            chip.append(ALPHABET.charAt((int)(number % ALPHABET_LENGTH)));
            number /= ALPHABET_LENGTH;
        }

        return chip.reverse().toString();
    }

    private static long decode(String key) {
        long number = 0L;
        for (int i = 0; i < key.length(); i++) {
            long pow = pow(key, i);
            number += pow * ALPHABET.indexOf(key.charAt(i));
        }

        return number;
    }

    private static long pow(String key, int i) {
        long pow = 1L;
        for (int j = 0; j < key.length() - i - 1; j++) {
            pow *= ALPHABET_LENGTH;
        }
        return pow;
    }

    public static void main(String[] args) {
        long seedNumber = 1233844L;
        String encodeStr = encode(seedNumber);
        System.out.println(encodeStr);

        long number = decode(encodeStr);
        System.out.println(number);

        System.out.println(number == seedNumber);
    }
}
