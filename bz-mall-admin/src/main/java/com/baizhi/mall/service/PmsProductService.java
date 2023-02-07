package com.baizhi.mall.service;

import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.dto.PmsProductDTO;
import com.baizhi.mall.dto.PmsProductParam;
import com.baizhi.mall.dto.PmsProductResult;
import com.baizhi.mall.entity.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductService extends IService<PmsProduct> {

    Result pageQuery(PmsProduct pmsProduct, Integer pageSize, Integer pageNum, String keyword);

    Integer updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    Integer updatePublishStatus(List<Long> ids, Integer publishStatus);

    Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    Integer updateNewStatus(List<Long> ids, Integer newStatus);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    Integer createProduct(PmsProductParam productParam);

    PmsProductResult getUpdateInfoById(Long id);

    Integer updateProduct(Long id, PmsProductParam productParam);

    Integer updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    @Transactional(propagation = Propagation.SUPPORTS)
    List<PmsProduct> listProduct(String keyword);

    PageInfo<PmsProduct> pageListProduct(PmsProductDTO productDTO, Integer pageNum, Integer pageSize);
}
