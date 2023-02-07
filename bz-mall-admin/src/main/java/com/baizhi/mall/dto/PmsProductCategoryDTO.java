package com.baizhi.mall.dto;

import lombok.Data;

import java.util.List;

@Data
public class PmsProductCategoryDTO {

    private Long parentId;

    private String name;

    /** 级别，根据parentId计算而来*/
    private Integer level;

    private String productUnit;

    private Integer navStatus;

    private Integer showStatus;

    private Integer sort;

    private String icon;

    private String keywords;

    private String description;

    /**记录当前商品类型 关联的 商品属性id*/
    private List<Long> productAttributeIdList;
}
