package com.baizhi.mall.service;

import com.baizhi.mall.entity.CmsSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

public interface CmsSubjectService extends IService<CmsSubject> {

    PageInfo<CmsSubject> likeQuery(String keyword, Integer pageSize, Integer pageNum);
}
