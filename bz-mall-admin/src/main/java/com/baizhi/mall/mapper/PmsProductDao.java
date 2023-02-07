package com.baizhi.mall.mapper;

import com.baizhi.mall.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;

public interface PmsProductDao {
    PmsProductResult selectUpdateInfoById(@Param("id")Long id);
}
