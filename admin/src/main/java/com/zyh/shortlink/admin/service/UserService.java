package com.zyh.shortlink.admin.service;

import com.zyh.shortlink.admin.dao.UserRegisterReqDto;
import com.zyh.shortlink.admin.dao.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 31748
* @description 针对表【user】的数据库操作Service
* @createDate 2025-07-25 10:51:14
*/
public interface UserService extends IService<User> {
    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return 用户古存在返回true， 不存在返回false
     */
    boolean hasUsername(String username);

    /**
     * 注册用户信息
     * @param requestParam
     */
    void register(UserRegisterReqDto requestParam);

}
