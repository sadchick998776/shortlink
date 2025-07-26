package com.zyh.shortlink.admin.controller;


import com.zyh.shortlink.admin.convention.result.Result;
import com.zyh.shortlink.admin.convention.result.Results;
import com.zyh.shortlink.admin.dto.req.UpdateUserReq;
import com.zyh.shortlink.admin.dto.req.UserLoginReq;
import com.zyh.shortlink.admin.dto.req.UserRegisterReqDto;
import com.zyh.shortlink.admin.dto.resp.UserLoginResp;
import com.zyh.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制层
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
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
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

    /**
     * 修改用户信息
     *
     * @param requestParam 根据参数修改用户信息
     */
    @PutMapping("/api/short-link/admin/v1/user")
    public void hasUsername(@RequestBody UpdateUserReq requestParam) {
        userService.updateUser(requestParam);
    }

    /**
     * 后台管理系统登录接口
     *
     * @param requestParam 根据参数登录后台管理系统
     * @return 返回 token
     */

    @PostMapping("/api/short-link/admin/v1/user/login")
    public Result<UserLoginResp> hasUsername(@RequestBody UserLoginReq requestParam) {
        return Results.success(userService.login(requestParam));
    }

    /**
     * 验证登录接口
     *
     * @param token 根据token进行登录
     * @return 返回状态码 true false
     */

    @GetMapping("/api/short-link/admin/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam String token, @RequestParam String username) {
        return Results.success(userService.checklogin(token, username));
    }

    @DeleteMapping("/api/short-link/admin/v1/user/logout")
    public void checkLogin(@RequestParam String username) {
        userService.exit(username);
    }


}
