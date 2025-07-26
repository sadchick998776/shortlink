package com.zyh.shortlink.admin.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 随机字符串生成工具类
 */
public class RandomStringUtil {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = UPPER.toLowerCase();
    private static final String DIGITS = "0123456789";
    private static final String ALPHA_NUM = UPPER + LOWER + DIGITS;
    private static final String CLEAR_CHARS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Random RANDOM = new Random();

    /**
     * 生成随机字符串（包含大小写字母和数字）
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomAlphaNumeric(int length) {
        return random(length, ALPHA_NUM);
    }

    /**
     * 生成6位随机字符（包含大小写字母和数字）
     *
     * @return 6位随机字符串
     */
    public static String random6() {
        return randomAlphaNumeric(6);
    }

    /**
     * 生成随机数字字符串
     *
     * @param length 字符串长度
     * @return 随机数字字符串
     */
    public static String randomNumeric(int length) {
        return random(length, DIGITS);
    }

    /**
     * 生成6位随机数字
     *
     * @return 6位随机数字
     */
    public static String random6Numeric() {
        return randomNumeric(6);
    }

    /**
     * 生成无歧义随机字符串（排除易混淆字符如0O1l等）
     *
     * @param length 字符串长度
     * @return 无歧义随机字符串
     */
    public static String randomClear(int length) {
        return random(length, CLEAR_CHARS);
    }

    /**
     * 生成6位无歧义随机字符
     *
     * @return 6位无歧义随机字符串
     */
    public static String random6Clear() {
        return randomClear(6);
    }

    /**
     * 使用安全随机数生成器生成随机字符串
     *
     * @param length 字符串长度
     * @param pool   字符池
     * @return 随机字符串
     */
    public static String secureRandom(int length, String pool) {
        if (length < 1) throw new IllegalArgumentException("长度必须大于0");
        if (pool == null || pool.isEmpty()) throw new IllegalArgumentException("字符池不能为空");

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(pool.length());
            sb.append(pool.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 使用普通随机数生成器生成随机字符串
     *
     * @param length 字符串长度
     * @param pool   字符池
     * @return 随机字符串
     */
    public static String random(int length, String pool) {
        if (length < 1) throw new IllegalArgumentException("长度必须大于0");
        if (pool == null || pool.isEmpty()) throw new IllegalArgumentException("字符池不能为空");

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(pool.length());
            sb.append(pool.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 生成UUID（无连字符）
     *
     * @return 32位UUID字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}