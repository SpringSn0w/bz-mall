package com.baizhi.mall.commons.api.vo;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class PageVO {

    private Integer pageNum;

    private Integer pageSize;

    private Integer totalPage;

    private Integer total;
}
