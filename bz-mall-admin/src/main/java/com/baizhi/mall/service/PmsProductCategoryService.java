package com.baizhi.mall.service;

import com.baizhi.mall.dto.PmsProductCategoryDTO;
import com.baizhi.mall.dto.PmsProductCategoryWithChildrenItem;
import com.baizhi.mall.entity.PmsProductCategory;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PmsProductCategoryService {

    Integer saveProductCategory(PmsProductCategoryDTO categoryDTO);

    PageInfo<PmsProductCategory> queryLimitByPrentId(Long parentId, Integer pageNum, Integer pageSize);

    Integer updateProductCategory(Long id, PmsProductCategoryDTO categoryDTO);

    List<PmsProductCategoryWithChildrenItem> childrenItemList();

    PmsProductCategory queryById(Long id);

    Integer deleteById(Long id);

    Integer updateNavStatus(List<Long> ids, Integer navStatus);

    Integer updateShowStatus(List<Long> ids, Integer showStatus);
}
