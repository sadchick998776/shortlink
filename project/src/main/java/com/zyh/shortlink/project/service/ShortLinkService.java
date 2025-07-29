package com.zyh.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyh.shortlink.project.dao.entity.ShortLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyh.shortlink.project.dto.req.ShortLinkCreateAndUpdateReq;
import com.zyh.shortlink.project.dto.req.ShortLinkPageReqDto;
import com.zyh.shortlink.project.dto.resp.GroupGidCountResp;
import com.zyh.shortlink.project.dto.resp.ShortLinkPageResp;
import com.zyh.shortlink.project.dto.resp.ShortLinkResp;

import java.util.List;
import java.util.Map;

/**
 * @author 31748
 * @description 针对表【t_link】的数据库操作Service
 * @createDate 2025-07-27 10:46:19
 */
public interface ShortLinkService extends IService<ShortLink> {
    /**
     * 添加短链接
     *
     * @param requestParam 根据参数封装bean添加短链接
     * @return
     */

    ShortLinkResp addShortLink(ShortLinkCreateAndUpdateReq requestParam);

    IPage<ShortLinkPageResp> pageShortLink(ShortLinkPageReqDto requestParam);

    /**
     * 根据参数统计短链接数量
     * @param requestParam 根据参数统计短链接数量参数
     * @return
     */

    List<GroupGidCountResp> shortLinkCountByGroupGid(List<String> requestParam);

    /**
     * 修改短链接
     *
     * @param requestParam 根据参数修改短链接
     */

//    void updateShortLink(ShortLinkCreateAndUpdateReq requestParam);
}
