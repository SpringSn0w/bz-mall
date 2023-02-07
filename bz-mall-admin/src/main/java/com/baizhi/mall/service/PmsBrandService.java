package com.baizhi.mall.service;

import com.baizhi.mall.dto.PmsBrandDTO;
import com.baizhi.mall.entity.PmsBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PmsBrandService extends IService<PmsBrand> {

    PageInfo<PmsBrand> queryListOrLike(String keyword,Integer showStatus,Integer pageNum,Integer pageSize);

    Integer savePms(PmsBrandDTO pmsBrandDTO);

    Integer modifyPms(Long id, PmsBrandDTO pmsBrandDTO);

    Integer modifyBatchShowStatus(List<Long> ids, Integer showStatus);

    Integer modifyBatchFactoryStatus(List<Long> ids, Integer factoryStatus);
}
