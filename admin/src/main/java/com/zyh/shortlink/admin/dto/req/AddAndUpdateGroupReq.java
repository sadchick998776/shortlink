package com.zyh.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 添加/修改短链接分组dto
 */
@Data
public class AddAndUpdateGroupReq {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;
}
