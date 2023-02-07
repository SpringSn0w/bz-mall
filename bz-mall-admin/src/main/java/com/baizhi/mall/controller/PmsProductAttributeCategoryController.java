package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.dto.ProductAttributeCategoryItem;
import com.baizhi.mall.entity.PmsProductAttributeCategory;
import com.baizhi.mall.exception.ApiExceptionEnumAssert;
import com.baizhi.mall.service.PmsProductAttributeCategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 15241
 */
@RestController
@ResultBody
@CrossOrigin
@RequestMapping("/productAttribute/category/")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService service;

    @PostMapping("create")
    public Integer savePac(String name){
        Integer count = service.createPmsProductAttributeCategory(name);
        ApiExceptionEnumAssert.CREAT.check(count);
        return count;
    }

    @PostMapping("/update/{id}")
    public Integer updatePac(@PathVariable("id") Long id, String name){
        Integer count = service.updatePmsProductAttributeCategory(id,name);
        ApiExceptionEnumAssert.UPDATE_BY_ID.check(count);
        return count;
    }

    @GetMapping("/delete/{id}")
    public Integer deletePac(@PathVariable("id") Long id){
        Integer count = service.removePac(id);
        ApiExceptionEnumAssert.DELETE_BY_ID.check(count);
        return count;
    }

    @GetMapping("{id}")
    public PmsProductAttributeCategory queryPac(@PathVariable("id") Long id){
        return service.queryById(id);
    }

    @GetMapping("/list")
    public PageInfo<PmsProductAttributeCategory> queryLimit(Integer pageNum,Integer pageSize){
        return service.queryByLimit(pageNum,pageSize);
    }

    @GetMapping("/list/withAttr")
    public List<ProductAttributeCategoryItem> queryAttr(){
        return service.queryCateggoryWithAttr();
    }
}
