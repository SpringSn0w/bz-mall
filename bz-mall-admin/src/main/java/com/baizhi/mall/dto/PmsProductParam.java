package com.baizhi.mall.dto;

import com.baizhi.mall.entity.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class PmsProductParam extends PmsProduct {
    private List<PmsProductLadder> productLadderList;
    private List<PmsProductFullReduction> productFullReductionList;
    private List<PmsMemberPrice> memberPriceList;
    private List<PmsSkuStock> skuStockList;
    private List<PmsProductAttributeValue> productAttributeValueList;
    private List<CmsSubjectProductRelation> subjectProductRelationList;
    private List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList;
}
