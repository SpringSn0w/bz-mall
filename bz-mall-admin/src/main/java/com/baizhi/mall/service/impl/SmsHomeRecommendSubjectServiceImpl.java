package com.baizhi.mall.service.impl;

import com.baizhi.mall.entity.SmsHomeRecommendSubject;
import com.baizhi.mall.mapper.SmsHomeRecommendSubjectMapper;
import com.baizhi.mall.service.SmsHomeRecommendSubjectService;
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
public class SmsHomeRecommendSubjectServiceImpl extends ServiceImpl<SmsHomeRecommendSubjectMapper, SmsHomeRecommendSubject>
        implements SmsHomeRecommendSubjectService {

    @Resource
    private SmsHomeRecommendSubjectMapper mapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageInfo<SmsHomeRecommendSubject> likeQuery(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<SmsHomeRecommendSubject> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(subjectName)){
        wrapper.like("subject_name",subjectName);
        }
        if (recommendStatus!=null){
            wrapper.eq("recommend_status",recommendStatus);
        }
        List<SmsHomeRecommendSubject> subjectList = mapper.selectList(wrapper);
        return new PageInfo<>(subjectList,(int)(mapper.selectCount(wrapper)));
    }
}
