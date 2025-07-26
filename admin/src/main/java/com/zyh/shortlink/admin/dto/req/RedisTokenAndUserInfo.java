package com.zyh.shortlink.admin.dto.req;

import lombok.Data;

/**
 * redis cache 避免重复登录token对象
 */
@Data
public class RedisTokenAndUserInfo {
    /**
     * token
     */
    private String token;
    /**
     * 用户基本信息对象
     */
    private UserInfo userInfo;
}
