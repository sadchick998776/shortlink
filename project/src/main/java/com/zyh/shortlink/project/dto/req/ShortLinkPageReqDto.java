package com.zyh.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Date;

/**
 * 分页查询短链接dto
 */
@Data
public class ShortLinkPageReqDto extends Page {
    /**
     * 分组标识
     */
    private String gid;
    /**
     * 排序字段
     */
    private String orderTag;

}
