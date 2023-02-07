package com.baizhi.mall.service.impl;

import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.entity.SmsHomeRecommendProduct;
import com.baizhi.mall.mapper.SmsHomeRecommendProductMapper;
import com.baizhi.mall.service.SmsHomeRecommendProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SmsHomeRecommendProductServiceImpl extends ServiceImpl<SmsHomeRecommendProductMapper, SmsHomeRecommendProduct>
        implements SmsHomeRecommendProductService {

    @Resource
    private SmsHomeRecommendProductMapper mapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Result queryLimit(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<SmsHomeRecommendProduct> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(productName)){
            wrapper.like("product_name",productName);
        }
        if (recommendStatus!=null){
            wrapper.eq("recommend_status",recommendStatus);
        }
        List<SmsHomeRecommendProduct> list = mapper.selectList(wrapper);
        PageInfo<SmsHomeRecommendProduct> info = new PageInfo<>(list,mapper.selectCount(wrapper));
        return Result.ok(info);
    }
}
