package com.baizhi.mall.service.impl;

import com.baizhi.mall.entity.CmsSubject;
import com.baizhi.mall.mapper.CmsSubjectMapper;
import com.baizhi.mall.service.CmsSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@Transactional
public class CmsSubjectServiceImpl extends ServiceImpl<CmsSubjectMapper, CmsSubject> implements CmsSubjectService {

    @Resource
    private CmsSubjectMapper mapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageInfo<CmsSubject> likeQuery(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<CmsSubject> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(keyword)){
            wrapper.like("title",keyword);
        }
        return new PageInfo<>(mapper.selectList(wrapper),mapper.selectCount(wrapper));
    }
}
