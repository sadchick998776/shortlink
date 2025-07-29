package com.zyh.shortlink.project.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyh.shortlink.project.convention.result.Result;
import com.zyh.shortlink.project.convention.result.Results;
import com.zyh.shortlink.project.dto.req.ShortLinkCreateAndUpdateReq;
import com.zyh.shortlink.project.dto.req.ShortLinkPageReqDto;
import com.zyh.shortlink.project.dto.resp.GroupGidCountResp;
import com.zyh.shortlink.project.dto.resp.ShortLinkPageResp;
import com.zyh.shortlink.project.dto.resp.ShortLinkResp;
import com.zyh.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接控制层
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;


    /**
     * 添加短链接
     *
     * @param requestParam 根据参数添加短链接
     */
    //todo 前期使用的admin后台管理系统http(HttpUtil)请求的方式默认使用的表单提交，后续使用openfeign进行改造
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkResp> addShortLink(ShortLinkCreateAndUpdateReq requestParam) {
        return Results.success(shortLinkService.addShortLink(requestParam));
    }

    /**
     * 分页查询短链接
     * @param requestParam 根据参数分页查询短链接
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageResp>> pageShortLink(ShortLinkPageReqDto requestParam) {
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }
    @GetMapping("/api/short-link/v1/count")
    public Result<List<GroupGidCountResp>> pageShortLink(List<String> requestParam) {
        return Results.success(shortLinkService.shortLinkCountByGroupGid(requestParam));
    }
}


