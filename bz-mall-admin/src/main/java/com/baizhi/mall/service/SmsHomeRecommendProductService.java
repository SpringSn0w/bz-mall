package com.baizhi.mall.service;

import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.entity.SmsHomeRecommendProduct;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SmsHomeRecommendProductService extends IService<SmsHomeRecommendProduct> {

    Result queryLimit(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
