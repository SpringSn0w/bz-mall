package com.baizhi.mall.service;

import com.baizhi.mall.dto.ProductAttributeCategoryItem;
import com.baizhi.mall.entity.PmsProductAttributeCategory;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PmsProductAttributeCategoryService {
    Integer createPmsProductAttributeCategory(String name);

    Integer updatePmsProductAttributeCategory(Long id, String name);

    Integer removePac(Long id);

    PmsProductAttributeCategory queryById(Long id);

    PageInfo<PmsProductAttributeCategory> queryByLimit(Integer pageNum, Integer pageSize);

    List<ProductAttributeCategoryItem> queryCateggoryWithAttr();
}
