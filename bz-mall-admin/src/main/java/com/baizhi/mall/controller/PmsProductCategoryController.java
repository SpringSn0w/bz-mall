package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.dto.PmsProductCategoryDTO;
import com.baizhi.mall.dto.PmsProductCategoryWithChildrenItem;
import com.baizhi.mall.entity.PmsProductCategory;
import com.baizhi.mall.exception.ApiExceptionEnumAssert;
import com.baizhi.mall.service.PmsProductCategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResultBody
@CrossOrigin
@RequestMapping("productCategory")
public class PmsProductCategoryController {


    @Autowired
    private PmsProductCategoryService service;

    @PostMapping("create")
    public Integer saveProductCategory(@RequestBody PmsProductCategoryDTO categoryDTO){
        Integer count = service.saveProductCategory(categoryDTO);
        ApiExceptionEnumAssert.UPDATE_BY_ID.check(count);
        return count;
    }

    @PostMapping("/update/{id}")
    public Integer updateProductCategory(@PathVariable("id") Long id,
                                         @RequestBody PmsProductCategoryDTO categoryDTO){
        Integer count = service.updateProductCategory(id,categoryDTO);
        ApiExceptionEnumAssert.UPDATE_BY_ID.check(count);
        return count;

    }

    @GetMapping("/list/{parentId}")
    public PageInfo<PmsProductCategory> queryLimitByPrentId(@PathVariable("parentId") Long parentId,
                                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "5") Integer pageSize){
        return service.queryLimitByPrentId(parentId,pageNum,pageSize);
    }

    @GetMapping("{id}")
    public PmsProductCategory queryById(@PathVariable("id") Long id){
        return service.queryById(id);
    }

    @PostMapping("delete/{id}")
    public Integer removeById(@PathVariable("id") Long id){
        return service.deleteById(id);
    }
    @PostMapping("/update/navStatus")
    public Integer updateNavStatus(@RequestParam List<Long> ids,Integer navStatus){
        Integer count = service.updateNavStatus(ids, navStatus);
        ApiExceptionEnumAssert.UPDATE_BY_EXAMPLE.check(count);
        return count;
    }
    @PostMapping("update/showStatus")
    public Integer updateShowStatus(@RequestParam List<Long> ids,Integer showStatus){
        Integer count = service.updateShowStatus(ids,showStatus);
        ApiExceptionEnumAssert.UPDATE_BY_EXAMPLE.check(count);
        return count;
    }

    @GetMapping("/list/withChildren")
    public List<PmsProductCategoryWithChildrenItem> childrenItemList(){
        return service.childrenItemList();
    }
}
