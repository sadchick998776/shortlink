package com.zyh.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyh.shortlink.project.convention.exception.ClientException;
import com.zyh.shortlink.project.convention.exception.ServiceException;
import com.zyh.shortlink.project.dao.entity.ShortLink;
import com.zyh.shortlink.project.dto.req.ShortLinkCreateAndUpdateReq;
import com.zyh.shortlink.project.dto.req.ShortLinkPageReqDto;
import com.zyh.shortlink.project.dto.resp.GroupGidCountResp;
import com.zyh.shortlink.project.dto.resp.ShortLinkPageResp;
import com.zyh.shortlink.project.dto.resp.ShortLinkResp;
import com.zyh.shortlink.project.service.ShortLinkService;
import com.zyh.shortlink.project.dao.mapper.ShortLinkMapper;
import com.zyh.shortlink.project.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.springframework.stereotype.Service;


import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.zyh.shortlink.project.convention.errorcode.BaseErrorCode.CLIENT_ERROR;

/**
 * @author 31748
 * @description 针对表【t_link】的数据库操作Service实现
 * @createDate 2025-07-27 10:46:19
 */
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLink>
        implements ShortLinkService {

    private final RBloomFilter<String> shortLinkRegisterCachePenetrationBloomFilter;

    @Override
    public ShortLinkResp addShortLink(ShortLinkCreateAndUpdateReq requestParam) {
        String domain = requestParam.getDomain();
        String originUrl = requestParam.getOriginUrl();
        String gid = requestParam.getGid();
        Integer createdType = requestParam.getCreatedType();
        Integer validDateType = requestParam.getValidDateType();
        String describe = requestParam.getDescribe();
        /**
         *参数为空抛出异常
         */
        if (ObjUtil.isNull(domain) ||
                ObjUtil.isNull(originUrl) ||
                ObjUtil.isNull(gid) ||
                ObjUtil.isNull(createdType) ||
                //可能长期有效不需要填写validDate字段以设置有效期
//                ObjUtil.isNull(validDate) ||
                ObjUtil.isNull(validDateType) ||
                ObjUtil.isNull(describe)
        ) {
            throw new ClientException(CLIENT_ERROR);
        }
        //分装bean
        ShortLink shortLink = BeanUtil.copyProperties(requestParam, ShortLink.class);
        String suffix = generateSuffix(requestParam);
        shortLink.setShortUri(suffix);
        String fullUrl = domain + "/" + suffix;
        //拼接域名和6位短链接后缀
        shortLink.setFullShortUrl(fullUrl);
//        boolean success = this.save(shortLink);
//        if (!success) {
//            throw new ServiceException(SERVICE_ERROR);
//        }
        try {
            save(shortLink);
        } catch (Exception message) {
            //todo 抛出异常(例如唯一索引重复)，数据库添加失败 布隆过滤器判断误判 缓存中不存在
            ShortLink queryShortLink = this.lambdaQuery()
                    .eq(ShortLink::getFullShortUrl, fullUrl)
                    .eq(ShortLink::getDelFlag, 0)
                    .one();
            if (ObjUtil.isNull(queryShortLink)) {
                log.error("短链接重复入库，抛出数据库层面错误,唯一索引重复 {}", message);
                throw new ServiceException("短链接入库重,添加失败");
            }
            throw new ServiceException("发生未知异常，数据库异常，添加短链接错误请排查数据库！");
        }
        shortLinkRegisterCachePenetrationBloomFilter.add(fullUrl);
        return ShortLinkResp.builder()
                .gid(gid)
                .originUrl(originUrl)
                .fullShortUrl(fullUrl)
                .build();
    }

    @Override
    public IPage<ShortLinkPageResp> pageShortLink(ShortLinkPageReqDto requestParam) {
        LambdaQueryWrapper<ShortLink> shortLinkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shortLinkLambdaQueryWrapper.eq(ShortLink::getGid, requestParam.getGid())
                .eq(ShortLink::getEnableStatus, 0)
                .eq(ShortLink::getDelFlag, 0);
        Page<ShortLink> shortLinkPageReturn = baseMapper.selectPage(requestParam, shortLinkLambdaQueryWrapper);
        return shortLinkPageReturn.convert(each -> BeanUtil.toBean(each, ShortLinkPageResp.class));

    }

    @Override
    public List<GroupGidCountResp> shortLinkCountByGroupGid(List<String> requestParam) {
        ArrayList<GroupGidCountResp> gidCounts = new ArrayList<>();
        for (String gid : requestParam) {
            Long count = this.lambdaQuery()
                    .eq(ShortLink::getGid, gid)
                    .eq(ShortLink::getEnableStatus, 0)
                    .eq(ShortLink::getDelFlag, 0)
                    .count();
            GroupGidCountResp groupGidCountResp = new GroupGidCountResp();
            groupGidCountResp.setGid(gid);
            groupGidCountResp.setShortLinkCount(Integer.valueOf(count.toString()));
        }
        return gidCounts;
    }

    // 原始url转化为6位的短链接后缀
    private String generateSuffix(ShortLinkCreateAndUpdateReq requestParam) {
        String domain = requestParam.getDomain();
        String originUrl = requestParam.getOriginUrl();
        String suffix = HashUtil.hashToBase62(originUrl);
        int suffixCount = 1;
        while (true) {
            if (suffixCount >= 10) {
                throw new ServiceException("短链接重复生成过多,请稍后重新尝试");
            }
            String fullShort = domain + "/" + suffix;

            if (!shortLinkRegisterCachePenetrationBloomFilter.contains(fullShort)) {
                return suffix;
            }
            originUrl += System.currentTimeMillis();
            suffix = HashUtil.hashToBase62(originUrl);
            suffixCount++;
        }

    }
}




