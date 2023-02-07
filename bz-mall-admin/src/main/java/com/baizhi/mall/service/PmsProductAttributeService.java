package com.baizhi.mall.service;

import com.baizhi.mall.dto.PmsProductAttributeDTO;
import com.baizhi.mall.dto.PmsProductAttributeInfo;
import com.baizhi.mall.entity.PmsProductAttribute;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PmsProductAttributeService {
    PageInfo<PmsProductAttribute> pageQuery(Long cid, Integer type, Integer pageNum, Integer pageSize);

    Integer savePmsProductAttribute(PmsProductAttributeDTO productAttributeDTO);

    Integer modifyPmsProductAttribute(Long id, PmsProductAttributeDTO productAttributeDTO);

    PmsProductAttribute queryPmsProductAttribute(Long id);

    Integer deleteBatch(List<Long> ids);

    List<PmsProductAttributeInfo> productAttributeInfoList(Long productCategoryId);
}
