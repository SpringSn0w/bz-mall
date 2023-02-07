package com.baizhi.mall.dto;

import lombok.Data;

@Data
public class PmsProductAttributeDTO {
    private Long productAttributeCategoryId;

    private String name;

    private Integer selectType;

    private Integer inputType;

    private String inputList;

    private Integer sort;

    private Integer filterType;

    private Integer searchType;

    private Integer relatedStatus;

    private Integer handAddStatus;

    private Integer type;
}
