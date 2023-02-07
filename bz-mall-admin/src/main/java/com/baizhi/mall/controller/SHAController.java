package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.entity.SmsHomeAdvertise;
import com.baizhi.mall.service.SmsHomeAdvertiseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 广告列表
 */
@RestController
@RequestMapping("/home/advertise")
@CrossOrigin
@ResultBody
public class SHAController {

    @Autowired
    private SmsHomeAdvertiseService service;


    @PostMapping("/delete")
    public Result<Integer> remove(@RequestParam("ids") List<Long> ids){
        service.removeByIds(ids);
        return Result.ok(ids.size());
    }

    @PostMapping("/create")
    public Result<Integer> saveShA(@RequestBody SmsHomeAdvertise advertise){
        service.save(advertise);
        return Result.ok(1);
    }

    @GetMapping("/update/{id}")
    public Result<Integer> updateAdv(@PathVariable Long id,@RequestBody SmsHomeAdvertise advertise){
        service.updateById(advertise);
        return Result.ok(1);
    }


    @GetMapping("{id}")
    public Result<SmsHomeAdvertise> detail(@PathVariable Long id){
        SmsHomeAdvertise advertise = service.getById(id);
        return Result.ok(advertise);
    }

    @PostMapping("/update/status/{id}")
    public Result<Integer> updateStatus(@PathVariable Long id, Integer status){
        SmsHomeAdvertise advertise = new SmsHomeAdvertise();
        advertise.setStatus(status);
        advertise.setId(id);
        service.updateById(advertise);
        return Result.ok(1);
    }
    @GetMapping("/list")
    public PageInfo<SmsHomeAdvertise> limitQuery(String name, Integer type, String endTime, Integer pageSize, Integer pageNum){
        return service.likeQuery(name, type, endTime, pageSize, pageNum);
    }
}
