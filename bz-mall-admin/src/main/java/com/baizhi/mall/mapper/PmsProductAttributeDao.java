package com.baizhi.mall.mapper;

import com.baizhi.mall.dto.PmsProductAttributeInfo;

import java.util.List;

public interface PmsProductAttributeDao {

    List<PmsProductAttributeInfo> pmsProductAttributeList(Long categoryId);
}
