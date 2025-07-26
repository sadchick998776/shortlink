package com.zyh.shortlink.admin.controller;


import com.zyh.shortlink.admin.convention.result.Result;
import com.zyh.shortlink.admin.convention.result.Results;
import com.zyh.shortlink.admin.dto.req.AddAndUpdateGroupReq;
import com.zyh.shortlink.admin.dto.req.UpdateUserReq;
import com.zyh.shortlink.admin.dto.req.UserLoginReq;
import com.zyh.shortlink.admin.dto.req.UserRegisterReqDto;
import com.zyh.shortlink.admin.dto.resp.ShortLinkGroupRespDto;
import com.zyh.shortlink.admin.dto.resp.UserLoginResp;
import com.zyh.shortlink.admin.service.GroupService;
import com.zyh.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分组控制层
 */
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    /**
     * 增加group分组
     *
     * @param requestParam 根据参数增加分组
     */
    @PostMapping("/api/short-link/admin/v1/group")
    public void addGroup(@RequestBody AddAndUpdateGroupReq requestParam) {
        groupService.addGroup(requestParam);
    }

    /**
     * 根据gid 修改 group名称
     *
     * @param requestParam  根据参数修改 group信息
     */


    @PutMapping("/api/short-link/admin/v1/group")
    public void putGroup(@RequestBody AddAndUpdateGroupReq requestParam) {
        groupService.putGroup(requestParam);
    }

    /**
     * 根据短链接gid删除短链接
     *
     * @param gid 根据参数删除短链接
     */
    @DeleteMapping("/api/short-link/admin/v1/group")
    public void delGroup(@RequestParam String gid) {
        groupService.delGroup(gid);
    }


    /**
     * 根据上下文获取用户名查询短链接信息
     *
     * @return 返回短链接集合信息
     */
    @GetMapping("/api/short-link/admin/v1/group")
    public Result<List<ShortLinkGroupRespDto>> queryGroupList() {
        return Results.success(groupService.queryGroupList());
    }


}
