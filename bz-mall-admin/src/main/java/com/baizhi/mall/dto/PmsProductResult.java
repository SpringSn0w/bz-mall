package com.baizhi.mall.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class PmsProductResult extends PmsProductParam{
    private String cateParentId;
}
