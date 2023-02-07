package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.entity.CmsSubject;
import com.baizhi.mall.service.CmsSubjectService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *专题推荐
 */
@RestController
@RequestMapping("/subject")
@ResultBody
public class CSController {

    @Autowired
    private CmsSubjectService service;

    @GetMapping("/listAll")
    public List<CmsSubject> queryAll(){
        return service.list(null);
    }

    @GetMapping("/list")
    public PageInfo<CmsSubject> termQuery(String keyword,Integer pageSize,Integer pageNum){

        return service.likeQuery(keyword,pageSize,pageNum);
    }
}
