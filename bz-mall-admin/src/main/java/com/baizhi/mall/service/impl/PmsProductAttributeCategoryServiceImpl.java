package com.baizhi.mall.service.impl;

import com.baizhi.mall.dto.ProductAttributeCategoryItem;
import com.baizhi.mall.entity.PmsProductAttributeCategory;
import com.baizhi.mall.mapper.PmsProductAttributeCategoryDao;
import com.baizhi.mall.mapper.PmsProductAttributeCategoryMapper;
import com.baizhi.mall.service.PmsProductAttributeCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Resource
    private PmsProductAttributeCategoryMapper mapper;

    @Resource
    private PmsProductAttributeCategoryDao dao;

    @Override
    public Integer createPmsProductAttributeCategory(String name) {
        PmsProductAttributeCategory category = new PmsProductAttributeCategory();
        category.setName(name);
        return mapper.insert(category);
    }

    @Override
    public Integer updatePmsProductAttributeCategory(Long id, String name) {
        PmsProductAttributeCategory category = new PmsProductAttributeCategory();
        category.setName(name);
        category.setId(id);
        return mapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public Integer removePac(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProductAttributeCategory queryById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<PmsProductAttributeCategory> queryByLimit(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PmsProductAttributeCategory> list = mapper.selectByExample(null);
        return new PageInfo<>(list);
    }

    @Override
    public List<ProductAttributeCategoryItem> queryCateggoryWithAttr() {
        return dao.listPmsProductAttributeCategoryWithAttribute();
    }
}
