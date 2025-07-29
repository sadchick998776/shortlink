package com.zyh.shortlink.admin.service;

import lombok.Data;

/**
 * 分追标识短链接统计vo
 */
@Data
public class GroupGidCountResp {
    /**
     * 分组标识
     */
    private String gid;
    /**
     * 分组标识下对应的短链接数量
     */
    private Integer ShortLinkCount;
}
