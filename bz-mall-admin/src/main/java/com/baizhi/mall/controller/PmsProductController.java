package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.dto.PmsProductDTO;
import com.baizhi.mall.dto.PmsProductParam;
import com.baizhi.mall.dto.PmsProductResult;
import com.baizhi.mall.entity.PmsProduct;
import com.baizhi.mall.service.PmsProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResultBody
@CrossOrigin
@RequestMapping("product")
public class PmsProductController {

    @Autowired
    private PmsProductService service;

    @PostMapping("create")
    public Integer create(@RequestBody PmsProductParam productParam){
        Integer count = service.createProduct(productParam);
        return count;
    }

    @GetMapping("updateInfo/{id}")
    public PmsProductResult getUpdateInfo(@PathVariable("id")Long id){
        return service.getUpdateInfoById(id);
    }

    @PostMapping("update/{id}")
    public Integer update(@PathVariable("id") Long id,
                          @RequestBody PmsProductParam productParam) {
        Integer count = service.updateProduct(id,productParam);
        return count;
    }

    @PostMapping("update/deleteStatus")
    public Integer updateDeleteStatus(List<Long> ids,Integer deleteStatus){
        return service.updateDeleteStatus(ids,deleteStatus);
    }

    @PostMapping("update/publishStatus")
    public Integer updatePublishStatus(List<Long> ids,Integer publishStatus){
        return service.updatePublishStatus(ids,publishStatus);
    }

    @PostMapping("update/recommendStatus")
    public Integer updateRecommendStatus(List<Long> ids,Integer recommendStatus){
        return service.updateRecommendStatus(ids,recommendStatus);
    }

    @PostMapping("update/newStatus")
    public Integer updateNewStatus(List<Long> ids,Integer newStatus){
        return service.updateNewStatus(ids,newStatus);
    }

    @PostMapping("update/verifyStatus")
    public Integer updateVerifyStatus(@RequestParam("ids") List<Long> ids, Integer verifyStatus, String detail) {
        Integer count = service.updateVerifyStatus(ids,verifyStatus,detail);
        return count;
    }

    @GetMapping("simpleList")
    public List<PmsProduct> listProduct(String keyword){
        List<PmsProduct> products = service.listProduct(keyword);
        return products;
    }

    @GetMapping("list")
    public PageInfo<PmsProduct> pageList(PmsProductDTO productDTO,
                                         @RequestParam(defaultValue = "1")Integer pageNum,
                                         @RequestParam(defaultValue = "5")Integer pageSize){
        return service.pageListProduct(productDTO,pageNum,pageSize);
    }
}
