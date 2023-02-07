package com.baizhi.mall.dto;

import lombok.Data;

@Data
public class PmsProductDTO {
    private Long brandId;

    private Long productCategoryId;

    private Integer publishStatus;

    private String productSn;

    private Integer verifyStatus;

    private String keyword;


}
