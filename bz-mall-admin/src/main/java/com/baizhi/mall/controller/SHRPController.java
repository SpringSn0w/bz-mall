package com.baizhi.mall.controller;


import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.entity.SmsHomeRecommendProduct;
import com.baizhi.mall.service.SmsHomeRecommendProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 人气推荐
 */
@RestController
@RequestMapping("/home/recommendProduct")
@CrossOrigin
public class SHRPController {

    @Autowired
    private SmsHomeRecommendProductService service;

    @PostMapping("/create")
    public Result homeShow(@RequestBody List<SmsHomeRecommendProduct> homeRecommendProductList){

        service.saveBatch(homeRecommendProductList);
        return Result.ok(homeRecommendProductList.size());
    }

    @GetMapping("/list")
    public Result pageQuery(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum) {

        return service.queryLimit(productName, recommendStatus, pageSize, pageNum);
    }

    @PostMapping("/update/sort/{id}")
    public Result updateStatus(@PathVariable Long id, Integer sort) {
        SmsHomeRecommendProduct product = new SmsHomeRecommendProduct()
                .setId(id).setSort(sort);
        service.updateById(product);
        return Result.ok(1);
    }

    @PostMapping("/delete")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        service.removeByIds(ids);
        return Result.ok(ids);
    }

    @PostMapping("/update/recommendStatus")
    public Result updateStatus(@RequestParam("ids") List<Long> ids,Integer recommendStatus){
        ArrayList<SmsHomeRecommendProduct> list = new ArrayList<>();
        for (Long id : ids) {
            SmsHomeRecommendProduct product = new SmsHomeRecommendProduct();
            product.setId(id).setRecommendStatus(recommendStatus);
            list.add(product);
        }
        service.updateBatchById(list);
        return Result.ok(list.size());
    }
}
