package com.baizhi.mall.service.impl;

import com.baizhi.mall.dto.PmsProductAttributeDTO;
import com.baizhi.mall.dto.PmsProductAttributeInfo;
import com.baizhi.mall.entity.PmsProductAttribute;
import com.baizhi.mall.entity.PmsProductAttributeCategory;
import com.baizhi.mall.entity.PmsProductAttributeExample;
import com.baizhi.mall.mapper.PmsProductAttributeCategoryMapper;
import com.baizhi.mall.mapper.PmsProductAttributeDao;
import com.baizhi.mall.mapper.PmsProductAttributeMapper;
import com.baizhi.mall.service.PmsProductAttributeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {

    @Resource
    private PmsProductAttributeMapper mapper;

    @Resource
    private PmsProductAttributeCategoryMapper categoryMapper;

    @Resource
    private PmsProductAttributeDao attributeDao;

    @Override
    public PageInfo<PmsProductAttribute> pageQuery(Long cid, Integer type, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria()
                .andProductAttributeCategoryIdEqualTo(cid)
                .andTypeEqualTo(type);
        PageInfo<PmsProductAttribute> info = new PageInfo<>(mapper.selectByExample(example));
        return info;
    }


    @Override
    public Integer savePmsProductAttribute(PmsProductAttributeDTO productAttributeDTO) {
        // dto  -> entity
        PmsProductAttribute attribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeDTO, attribute);

        // 通过type是规格还是参数来进行属性分类表里的操作
        PmsProductAttributeCategory category = categoryMapper.selectByPrimaryKey(attribute.getProductAttributeCategoryId());
        if (Integer.valueOf(0).equals(attribute.getType())) {
            category.setAttributeCount(category.getAttributeCount() + 1);
        } else if (Integer.valueOf(1).equals(attribute.getType())) {
            category.setParamCount(category.getParamCount() + 1);
        }
        categoryMapper.updateByPrimaryKeySelective(category);
        return mapper.insert(attribute);
    }

    @Override
    public Integer modifyPmsProductAttribute(Long id, PmsProductAttributeDTO productAttributeDTO) {
        PmsProductAttribute attribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeDTO, attribute);
        attribute.setId(id);
        return mapper.updateByPrimaryKeySelective(attribute);
    }

    @Override
    public PmsProductAttribute queryPmsProductAttribute(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            PmsProductAttribute attribute = mapper.selectByPrimaryKey(id);
            if (attribute != null) {
                PmsProductAttributeCategory category = categoryMapper.selectByPrimaryKey(id);
                if (Integer.valueOf(0).equals(attribute.getType())) {
                    category.setAttributeCount(category.getAttributeCount() -1);
                } else if (Integer.valueOf(1).equals(attribute.getType())) {
                    category.setParamCount(category.getParamCount() - 1);
                }
                categoryMapper.updateByPrimaryKeySelective(category);
            }
        }
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        return mapper.deleteByExample(example);
    }

    @Override
    public List<PmsProductAttributeInfo> productAttributeInfoList(Long productCategoryId) {
        return attributeDao.pmsProductAttributeList(productCategoryId);
    }
}
