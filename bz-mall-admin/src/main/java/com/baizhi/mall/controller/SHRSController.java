package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.entity.SmsHomeRecommendSubject;
import com.baizhi.mall.service.SmsHomeRecommendSubjectService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 专题推荐
 */
@RestController
@RequestMapping("/home/recommendSubject")
@CrossOrigin
@ResultBody
public class SHRSController {

    @Autowired
    private SmsHomeRecommendSubjectService service;



    @PostMapping("/create")
    public Result newInsert(@RequestBody List<SmsHomeRecommendSubject> subject){
        service.saveBatch(subject);
        return Result.ok();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam("ids") List<Long> ids){
        service.removeByIds(ids);
        return Result.ok(ids.size());
    }

    @PostMapping("/update/sort/{id}")
    public Result updateStatus(@PathVariable Long id,Integer sort){
        SmsHomeRecommendSubject subject = new SmsHomeRecommendSubject().setId(id).setSort(sort);
        service.updateById(subject);
        return Result.ok(1);
    }

    @PostMapping("/update/recommendStatus")
    public Result batchUpdateStatus(@RequestParam("ids") List<Long> ids, Integer recommendStatus){
        List<SmsHomeRecommendSubject> list = new ArrayList<>();
        for (Long id : ids) {
            SmsHomeRecommendSubject subject = new SmsHomeRecommendSubject();
            subject.setId(id).setRecommendStatus(recommendStatus);
            list.add(subject);
        }
        service.updateBatchById(list);
        return Result.ok(ids.size());
    }

    @GetMapping("/list")
    public PageInfo<SmsHomeRecommendSubject> queryLimit(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum){

        return service.likeQuery(subjectName, recommendStatus, pageSize, pageNum);
    }
}
