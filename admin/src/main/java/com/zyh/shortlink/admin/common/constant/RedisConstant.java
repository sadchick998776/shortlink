package com.zyh.shortlink.admin.common.constant;

import lombok.RequiredArgsConstructor;

/**
 * 短链接后台系统 redis 缓存产量类
 */
@RequiredArgsConstructor
public class RedisConstant {

        public static final String LOCK_USER_REGISTER_KEY = "short-link:register_username:";

}
