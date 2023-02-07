package com.baizhi.mall.mapper;

import com.baizhi.mall.dto.ProductAttributeCategoryItem;

import java.util.List;

public interface PmsProductAttributeCategoryDao {
    /**多表，查询所有属性的同时，级联查询分类下的所有type=1的属性*/
    List<ProductAttributeCategoryItem> listPmsProductAttributeCategoryWithAttribute();
}
