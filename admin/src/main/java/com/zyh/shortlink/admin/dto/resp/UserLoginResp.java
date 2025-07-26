package com.zyh.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * 用户登录接口返回响应
 */
@Data
public class UserLoginResp {
    /**
     * 登录 token
     */
    private String token;
}
