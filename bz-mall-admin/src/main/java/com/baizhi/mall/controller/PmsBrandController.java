package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.dto.PmsBrandDTO;
import com.baizhi.mall.entity.PmsBrand;
import com.baizhi.mall.exception.ApiExceptionEnumAssert;
import com.baizhi.mall.service.PmsBrandService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@ResultBody
@CrossOrigin
public class PmsBrandController {

    @Autowired
    private PmsBrandService service;

    @GetMapping("listAll")
    public List<PmsBrand> queryAll() {
        return service.list(null);
    }

    @GetMapping("{id}")
    public PmsBrand queryById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @GetMapping("/list")
    public PageInfo<PmsBrand> queryLimit(String keyword, Integer showStatus, Integer pageNum, Integer pageSize){
        return service.queryListOrLike(keyword, showStatus, pageNum, pageSize);
    }

    @PostMapping("/create")
    public Integer createPms(@RequestBody PmsBrandDTO pmsBrandDTO){
        Integer count = service.savePms(pmsBrandDTO);
        ApiExceptionEnumAssert.CREAT.check(count);
        return count;
    }

    @PostMapping("/update/{id}")
    public Integer updatePms(@PathVariable("id") Long id,@RequestBody PmsBrandDTO pmsBrandDTO){
        Integer count = service.modifyPms(id,pmsBrandDTO);
        ApiExceptionEnumAssert.UPDATE_BY_ID.check(count);
        return count;
    }

    @PostMapping("/update/showStatus")
    public Integer updateBatchShowStatus(@RequestParam("ids") List<Long> ids,Integer showStatus){
        Integer count = service.modifyBatchShowStatus(ids, showStatus);
        ApiExceptionEnumAssert.UPDATE_BY_EXAMPLE.check(count);
        return count;
    }

    @PostMapping("/update/factoryStatus")
    public Integer updateBatchFactoryStatus(@RequestParam("ids") List<Long> ids,Integer factoryStatus){
        Integer count = service.modifyBatchFactoryStatus(ids,factoryStatus);
        ApiExceptionEnumAssert.UPDATE_BY_EXAMPLE.check(count);
        return count;
    }

    @GetMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        service.removeById(id);
        return Result.ok();
    }

    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids){
        service.removeByIds(ids);
        return Result.ok();
    }

}
