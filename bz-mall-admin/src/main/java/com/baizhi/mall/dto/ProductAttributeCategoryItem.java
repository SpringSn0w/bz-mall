package com.baizhi.mall.dto;

import com.baizhi.mall.entity.PmsProductAttribute;
import com.baizhi.mall.entity.PmsProductAttributeCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用于多表
 */
public class ProductAttributeCategoryItem extends PmsProductAttributeCategory {
    @Getter
    @Setter
    private List<PmsProductAttribute> productAttributes;
}
