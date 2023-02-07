package com.baizhi.mall.service;

import com.baizhi.mall.entity.SmsHomeRecommendSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

public interface SmsHomeRecommendSubjectService extends IService<SmsHomeRecommendSubject> {
    PageInfo<SmsHomeRecommendSubject> likeQuery(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
