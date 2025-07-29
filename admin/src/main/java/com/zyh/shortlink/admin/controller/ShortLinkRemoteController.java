package com.zyh.shortlink.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyh.shortlink.admin.convention.result.Result;
import com.zyh.shortlink.admin.convention.result.Results;
import com.zyh.shortlink.admin.remote.dto.req.ShortLinkCreateAndUpdateReq;
import com.zyh.shortlink.admin.remote.dto.req.ShortLinkPageReqDto;
import com.zyh.shortlink.admin.remote.dto.resp.ShortLinkPageResp;
import com.zyh.shortlink.admin.remote.dto.resp.ShortLinkResp;
import com.zyh.shortlink.admin.remote.dto.service.ShortLinkRemote;
import com.zyh.shortlink.admin.service.GroupGidCountResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 分组控制层
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkRemoteController {
    private ShortLinkRemote shortLinkRemote = new ShortLinkRemote() {
    };

    /**
     * 增加短链接
     * @param requestParam 根据参数增加短链接
     * @return
     */

    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkResp> addShortLink(@RequestBody ShortLinkCreateAndUpdateReq requestParam) {
        return shortLinkRemote.addShortLink(requestParam);
    }

    /**
     * 分页短链接
     * @param requestParam 根据参数分页短链接
     * @return
     */

    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageResp>> pageShortLink(ShortLinkPageReqDto requestParam) {
        return shortLinkRemote.pageShortLink(requestParam);
    }



    @GetMapping("/api/short-link/admin/v1/page")
    public Result<List<GroupGidCountResp>> pageShortLink(@RequestParam("requestParam") List<String> requestParam) {
        return shortLinkRemote.shortLinkCountByGroupGid(requestParam);
    }












}
