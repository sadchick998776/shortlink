package com.zyh.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 用户登录接口
 */
@Data
public class UserLoginReq {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
}
