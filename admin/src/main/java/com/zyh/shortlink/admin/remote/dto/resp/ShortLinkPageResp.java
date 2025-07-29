package com.zyh.shortlink.admin.remote.dto.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 分组查询短链接项目vo
 */
@Data
public class ShortLinkPageResp {
    /**
     * 域名
     */
    private String domain;

    /**
     * 短链接
     */
    private String shortUri;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 有效期类型 0: 永久有效 1: 自定义
     */
    private Integer validDateType;

    /**
     * 有效期
     */
    private Date validDate;
    /**
     * 网站标识
     */
    @TableField("website_tag")
    private String website_tag;
}
