package com.zyh.shortlink.admin.remote.dto.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.hash.Hash;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.zyh.shortlink.admin.convention.result.Result;
import com.zyh.shortlink.admin.remote.dto.req.ShortLinkCreateAndUpdateReq;
import com.zyh.shortlink.admin.remote.dto.req.ShortLinkPageReqDto;
import com.zyh.shortlink.admin.remote.dto.resp.ShortLinkPageResp;
import com.zyh.shortlink.admin.remote.dto.resp.ShortLinkResp;
import com.zyh.shortlink.admin.service.GroupGidCountResp;
import java.util.List;
import java.util.Map;

/**
 * 后台管理系统远程调用短链接
 */
public interface ShortLinkRemote {
    default Result<ShortLinkResp> addShortLink(ShortLinkCreateAndUpdateReq requestParam) {
        Map<String, Object> hashmap = BeanUtil.beanToMap(requestParam);
        String str = HttpUtil.post("http://127.0.0.1:8001/api/short-link/admin/v1/create", hashmap);
        return JSON.parseObject(str, new TypeReference<Result<ShortLinkResp>>() {
        });

    }

    default Result<IPage<ShortLinkPageResp>> pageShortLink(ShortLinkPageReqDto requestParam) {
        Map<String, Object> hashmap = BeanUtil.beanToMap(requestParam);
        String str = HttpUtil.get("http://127.0.0.1:8001/api/short-link/admin/v1/page", hashmap);
        return JSON.parseObject(str, new TypeReference<Result<IPage<ShortLinkPageResp>>>() {
        });


    }

    default Result<List<GroupGidCountResp>> shortLinkCountByGroupGid(List<String> requestParam){
        Map<String, Object> hashmap = BeanUtil.beanToMap(requestParam);
        String str = HttpUtil.post("http://127.0.0.1:8000/api/short-link/v1/count", hashmap);
        return JSON.parseObject(str, new TypeReference<Result<List<GroupGidCountResp>>>() {
        });
    }
}
