package com.baizhi.mall.service.impl;

import com.baizhi.mall.dto.PmsProductCategoryDTO;
import com.baizhi.mall.dto.PmsProductCategoryWithChildrenItem;
import com.baizhi.mall.entity.PmsProductCategory;
import com.baizhi.mall.entity.PmsProductCategoryAttributeRelation;
import com.baizhi.mall.entity.PmsProductCategoryAttributeRelationExample;
import com.baizhi.mall.entity.PmsProductCategoryExample;
import com.baizhi.mall.mapper.PmsProductCategoryAttributeRelationMapper;
import com.baizhi.mall.mapper.PmsProductCategoryDao;
import com.baizhi.mall.mapper.PmsProductCategoryMapper;
import com.baizhi.mall.service.PmsProductCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Resource
    private PmsProductCategoryMapper mapper;
    @Resource
    private PmsProductCategoryAttributeRelationMapper relationMapper;
    @Resource
    private PmsProductCategoryDao dao;

    @Override
    public Integer saveProductCategory(PmsProductCategoryDTO categoryDTO) {
        // dto -> entity
        PmsProductCategory category = new PmsProductCategory();
        BeanUtils.copyProperties(categoryDTO, category);
        // 不全没有添加的属性
        category.setProductCount(0);
        // level ==父level不存在 ? 0 : level+1
        setCategory(category);

        int count = mapper.insert(category);

        // 记录 商品类别 和 哪些商品属性有关系
        List<Long> attributeIdList = categoryDTO.getProductAttributeIdList();
        insertCategoryAttributeRelation(category, attributeIdList);
        return count;
    }

    private void insertCategoryAttributeRelation(PmsProductCategory category, List<Long> attributeIdList) {
        if (!CollectionUtils.isEmpty(attributeIdList)) {
            // 记录关系
            for (Long attributeId : attributeIdList) {
                PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
                relation.setProductCategoryId(category.getId());
                relation.setProductAttributeId(attributeId);
                relationMapper.insert(relation);
            }
        }
    }

    private void setCategory(PmsProductCategory category) {
        if (category.getParentId() == null || category.getParentId() == 0) {
            category.setLevel(0);
        } else {
            // 根据 parentId查询父level
            PmsProductCategory productCategory = mapper.selectByPrimaryKey(category.getParentId());
            if (productCategory != null) {
                category.setLevel(productCategory.getLevel() + 1);
            } else {
                category.setLevel(0);
            }
        }
    }

    @Override
    public PageInfo<PmsProductCategory> queryLimitByPrentId(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        PmsProductCategoryExample.Criteria criteria = example.createCriteria();
        if (parentId != null) {
            criteria.andParentIdEqualTo(parentId);
        }
        example.setOrderByClause("sort desc");
        List<PmsProductCategory> list = mapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public Integer updateProductCategory(Long id, PmsProductCategoryDTO categoryDTO) {
        PmsProductCategory category = new PmsProductCategory();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setId(id);

        // 设置当前表的level
        setCategory(category);
        int count = mapper.updateByPrimaryKeySelective(category);

        // 跟新关系表
        List<Long> longList = categoryDTO.getProductAttributeIdList();
        // 删除旧有关系添加新关系
        // delete from tab where product_category_id = ?
        PmsProductCategoryAttributeRelationExample example = new PmsProductCategoryAttributeRelationExample();
        example.createCriteria()
                .andProductCategoryIdEqualTo(id);
        relationMapper.deleteByExample(example);
        if (!CollectionUtils.isEmpty(longList)) {
            insertCategoryAttributeRelation(category, longList);
        }

        return count;
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> childrenItemList() {
        return dao.listCategoryWithChildren();
    }

    @Override
    public PmsProductCategory queryById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteById(Long id) {
        int count = mapper.deleteByPrimaryKey(id);
        if (count == 1){
            PmsProductCategoryAttributeRelationExample example = new PmsProductCategoryAttributeRelationExample();
            example.createCriteria().andProductCategoryIdEqualTo(id);
            relationMapper.deleteByExample(example);
        }
        return count;
    }

    @Override
    public Integer updateNavStatus(List<Long> ids, Integer navStatus) {
        PmsProductCategory category = new PmsProductCategory();
        category.setNavStatus(navStatus);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria()
                .andIdIn(ids);
        return mapper.updateByExampleSelective(category,example);
    }

    @Override
    public Integer updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsProductCategory category = new PmsProductCategory();
        category.setShowStatus(showStatus);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria()
                .andIdIn(ids);
        return mapper.updateByExampleSelective(category,example);
    }
}
