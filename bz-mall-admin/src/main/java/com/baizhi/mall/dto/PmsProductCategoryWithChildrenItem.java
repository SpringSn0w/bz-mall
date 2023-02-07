package com.baizhi.mall.dto;

import com.baizhi.mall.entity.PmsProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {

    @Getter
    @Setter
    private List<PmsProductCategory> children;
}
