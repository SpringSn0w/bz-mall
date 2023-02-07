package com.baizhi.mall.mapper;

import com.baizhi.mall.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

public interface PmsProductCategoryDao {

    List<PmsProductCategoryWithChildrenItem> listCategoryWithChildren();
}
