package com.zyh.shortlink.admin.controller;


import com.zyh.shortlink.admin.convention.result.Result;
import com.zyh.shortlink.admin.convention.result.Results;
import com.zyh.shortlink.admin.dao.UserRegisterReqDto;
import com.zyh.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口层
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return Result.data  存在 true  不存在  false
     */

    @GetMapping("/api/short-link/v1/user/has-username")
    public Result hasUsername(@RequestParam("username") String username) {
        return Results.success(userService.hasUsername(username));
    }


    /**
     * 注册用户信息
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/admin/v1/user")
    public void  hasUsername(@RequestBody UserRegisterReqDto requestParam) {
        userService.register(requestParam);
    }



}
