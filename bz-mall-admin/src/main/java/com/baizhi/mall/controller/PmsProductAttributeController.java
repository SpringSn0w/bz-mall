package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.dto.PmsProductAttributeDTO;
import com.baizhi.mall.dto.PmsProductAttributeInfo;
import com.baizhi.mall.entity.PmsProductAttribute;
import com.baizhi.mall.exception.ApiExceptionEnumAssert;
import com.baizhi.mall.service.PmsProductAttributeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResultBody
@CrossOrigin
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService service;

    @GetMapping("/list/{cid}")
    public PageInfo<PmsProductAttribute> pageQuery(@PathVariable("cid") Long cid, Integer type,
                                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "5") Integer pageSize){
        return service.pageQuery(cid,type,pageNum,pageSize);
    }

    @PostMapping("create")
    public Integer savePmsProductAttribute(@RequestBody PmsProductAttributeDTO productAttributeDTO){
        Integer count = service.savePmsProductAttribute(productAttributeDTO);
        ApiExceptionEnumAssert.CREAT.check(count);
        return count;
    }

    @PostMapping("update/{id}")
    public Integer modifyPmsProductAttribute(@PathVariable("id") Long id,
                                             @RequestBody PmsProductAttributeDTO productAttributeDTO){

        Integer count = service.modifyPmsProductAttribute(id,productAttributeDTO);
        ApiExceptionEnumAssert.UPDATE_BY_ID.check(count);
        return count;
    }

    @GetMapping("{id}")
    public PmsProductAttribute queryPmsProductAttribute(@PathVariable("id") Long id){
        return service.queryPmsProductAttribute(id);
    }

    @PostMapping("delete")
    public Integer deleteBatch(@RequestParam List<Long> ids){
        Integer count = service.deleteBatch(ids);
        ApiExceptionEnumAssert.DELETE_BY_EXAMPLE.check(count);
        return count;
    }

    @GetMapping("attrInfo/{productCategoryId}")
    public List<PmsProductAttributeInfo> productAttributeInfoList(@PathVariable("productCategoryId") Long productCategoryId){
        return service.productAttributeInfoList(productCategoryId);
    }
}
