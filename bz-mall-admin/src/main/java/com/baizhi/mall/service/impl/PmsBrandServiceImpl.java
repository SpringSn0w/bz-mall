package com.baizhi.mall.service.impl;

import com.baizhi.mall.dto.PmsBrandDTO;
import com.baizhi.mall.entity.PmsBrand;
import com.baizhi.mall.entity.PmsBrandExample;
import com.baizhi.mall.entity.PmsProduct;
import com.baizhi.mall.entity.PmsProductExample;
import com.baizhi.mall.mapper.PmsBrandMapper;
import com.baizhi.mall.mapper.PmsProductMapper;
import com.baizhi.mall.service.PmsBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper,PmsBrand> implements PmsBrandService {

    @Resource
    private PmsBrandMapper mapper;

    @Resource
    private PmsProductMapper productMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageInfo<PmsBrand> queryListOrLike(String keyword, Integer showStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PmsBrandExample example = new PmsBrandExample();
        PmsBrandExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)){
            criteria.andNameLike("%"+keyword+"%");
        }
        if (showStatus!=null){
            criteria.andShowStatusEqualTo(showStatus);
        }
        List<PmsBrand> pmsBrands = mapper.selectByExample(example);
        return new PageInfo<>(pmsBrands, (int) mapper.countByExample(example));
    }

    @Override
    public Integer savePms(PmsBrandDTO pmsBrandDTO) {
        PmsBrand brand = new PmsBrand();
        // dto -> entity
        BeanUtils.copyProperties(pmsBrandDTO,brand);
        // 其他属性赋值
        brand.setProductCount(0);
        brand.setProductCommentCount(0);

        if (StringUtils.isEmpty(brand.getFirstLetter())){
            brand.setFirstLetter(brand.getName().substring(0,1));
        }
        return mapper.insert(brand);
    }

    @Override
    public Integer modifyPms(Long id, PmsBrandDTO pmsBrandDTO) {
        PmsBrand brand = new PmsBrand();
        // dto -> entity
        BeanUtils.copyProperties(pmsBrandDTO,brand);
        brand.setId(id);

        // 如果修改品牌名，商品表的品牌名随之修改
        if (!StringUtils.isEmpty(brand.getName())){
            PmsProductExample example = new PmsProductExample();
            example.createCriteria()
                            .andBrandIdEqualTo(brand.getId());

            PmsProduct product = new PmsProduct();
            product.setName(brand.getName());
            productMapper.updateByExampleSelective(product,example);
        }
        return mapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public Integer modifyBatchShowStatus(List<Long> ids, Integer showStatus) {
        PmsBrandExample example = new PmsBrandExample();
        PmsBrandExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        PmsBrand brand = new PmsBrand();
        brand.setShowStatus(showStatus);
        return mapper.updateByExampleSelective(brand,example);
    }

    @Override
    public Integer modifyBatchFactoryStatus(List<Long> ids, Integer factoryStatus) {
        PmsBrandExample example = new PmsBrandExample();
        PmsBrandExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        PmsBrand brand = new PmsBrand();
        brand.setFactoryStatus(factoryStatus);
        return mapper.updateByExampleSelective(brand,example);
    }
}
