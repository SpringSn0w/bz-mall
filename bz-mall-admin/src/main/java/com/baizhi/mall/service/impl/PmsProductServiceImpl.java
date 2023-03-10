package com.baizhi.mall.service.impl;

import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.dto.PmsProductDTO;
import com.baizhi.mall.dto.PmsProductParam;
import com.baizhi.mall.dto.PmsProductResult;
import com.baizhi.mall.entity.*;
import com.baizhi.mall.mapper.*;
import com.baizhi.mall.service.PmsProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

    @Resource
    private PmsProductMapper mapper;
    @Resource
    private PmsProductVertifyRecordMapper productVertifyRecordMapper;

    @Resource
    private PmsMemberPriceMapper memberPriceMapper;
    @Resource
    private PmsProductLadderMapper productLadderMapper;
    @Resource
    private PmsProductFullReductionMapper productFullReductionMapper;
    @Resource
    private PmsSkuStockMapper skuStockMapper;
    @Resource
    private PmsProductAttributeValueMapper productAttributeValueMapper;
    @Resource
    private CmsSubjectProductRelationMapper subjectProductRelationMapper;
    @Resource
    private CmsPrefrenceAreaProductRelationMapper productRelationMapper;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Result pageQuery(PmsProduct pmsProduct, Integer pageSize, Integer pageNum, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<PmsProduct> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(keyword)){
            wrapper.like("name",keyword);
        }
        List<PmsProduct> list = mapper.selectList(wrapper);
        PageInfo<PmsProduct> info = new PageInfo<>(list,mapper.selectCount(wrapper));
        return Result.ok(info);
    }

    @Override
    public Integer updateDeleteStatus(@RequestParam("ids") List<Long> ids, Integer deleteStatus) {
        PmsProduct product = new PmsProduct();
        product.setDeleteStatus(deleteStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria()
                .andIdIn(ids);
        return mapper.updateByExampleSelective(product,example);
    }

    @Override
    public Integer updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PmsProduct product = new PmsProduct();
        product.setPublishStatus(publishStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria()
                .andIdIn(ids);
        return mapper.updateByExampleSelective(product,example);
    }

    @Override
    public Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct product = new PmsProduct();
        product.setRecommandStatus(recommendStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria()
                .andIdIn(ids);
        return mapper.updateByExampleSelective(product,example);
    }

    @Override
    public Integer updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct product = new PmsProduct();
        product.setNewStatus(newStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria()
                .andIdIn(ids);
        return mapper.updateByExampleSelective(product,example);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer createProduct(PmsProductParam productParam) {
        // ??????????????????????????????????????????
        int count = mapper.insertSelective(productParam);

        //???????????????????????????????????????
        Long productId = productParam.getId();
        //1 ??????????????????
        List<PmsMemberPrice> memberPriceList = productParam.getMemberPriceList();
        //???????????????????????????????????????id??????????????????????????????????????????????????????
        insertMemberPriceList(productId, memberPriceList);

        //2 ????????????
        List<PmsProductLadder> productLadderList = productParam.getProductLadderList();
        insertProductLadderList(productId, productLadderList);
        //3 ????????????
        List<PmsProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        insertProductFullReductionList(productId, productFullReductionList);

        //4 ???????????????
        List<PmsProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        insertProductAttributeValueList(productId, productAttributeValueList);

        //5 ??????????????????
        List<CmsSubjectProductRelation> subjectProductRelationList = productParam.getSubjectProductRelationList();
        insertSubjectProductRelationList(productId, subjectProductRelationList);

        //6 ??????????????????
        List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList = productParam.getPrefrenceAreaProductRelationList();
        insertPrefrenceAreaProductRelationList(productId, prefrenceAreaProductRelationList);

        //7 sku
        // yyyyMMdd+productId?????????+?????????
        List<PmsSkuStock> skuStockList = productParam.getSkuStockList();
        insertSkuStockList(productId, skuStockList);

        return count;
    }

    private void handleSkuCode(Long productId,List<PmsSkuStock> skuStockList){
        PmsSkuStockExample example = new PmsSkuStockExample();
        example.createCriteria().andProductIdEqualTo(productId);
        List<PmsSkuStock> pmsSkuStocks = skuStockMapper.selectByExample(example);
        int num = 0;
        if (!CollectionUtils.isEmpty(pmsSkuStocks)) {
            //?????????????????????????????????
            Collections.sort(pmsSkuStocks, new Comparator<PmsSkuStock>() {
                @Override
                //???????????????2???????????????????????????
                public int compare(PmsSkuStock o1, PmsSkuStock o2) {
                    return (int) (Long.parseLong(o1.getSkuCode()) - Long.parseLong(o2.getSkuCode()));
                }
            });

            num = (int)(Long.parseLong(pmsSkuStocks.get(pmsSkuStocks.size() - 1).getSkuCode()) % 1000);
        }

        if (!CollectionUtils.isEmpty(skuStockList)) {
            for (int i = 0; i < skuStockList.size(); i++) {
                PmsSkuStock skuStock = skuStockList.get(i);
                skuStock.setProductId(productId);
                if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    StringBuilder sb = new StringBuilder(simpleDateFormat.format(date));
                    sb.append(String.format("%04d", productId));
                    sb.append(String.format("%03d",  ++num));
                    skuStock.setSkuCode(sb.toString());
                }
            }
        }
    }

    public void insertSkuStockList(Long productId, List<PmsSkuStock> skuStockList) {
        handleSkuCode(productId,skuStockList);
        if (!CollectionUtils.isEmpty(skuStockList)) {
            for (PmsSkuStock pmsSkuStock : skuStockList) {
                skuStockMapper.insert(pmsSkuStock);
            }
        }
    }

    private void insertPrefrenceAreaProductRelationList(Long productId, List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList) {
        if (!CollectionUtils.isEmpty(prefrenceAreaProductRelationList)) {
            for (CmsPrefrenceAreaProductRelation prefrenceAreaProductRelation : prefrenceAreaProductRelationList) {
                prefrenceAreaProductRelation.setProductId(productId);
                productRelationMapper.insert(prefrenceAreaProductRelation);
            }

        }
    }

    private void insertSubjectProductRelationList(Long productId, List<CmsSubjectProductRelation> subjectProductRelationList) {
        if (!CollectionUtils.isEmpty(subjectProductRelationList)) {
            subjectProductRelationList.forEach(subjectProductRelation -> {
                subjectProductRelation.setProductId(productId);
                subjectProductRelationMapper.insert(subjectProductRelation);
            });
        }
    }

    private void insertProductAttributeValueList(Long productId, List<PmsProductAttributeValue> productAttributeValueList) {
        if (!CollectionUtils.isEmpty(productAttributeValueList)) {
            productAttributeValueList.forEach(productAttributeValue -> {
                productAttributeValue.setProductId(productId);
                productAttributeValueMapper.insert(productAttributeValue);
            });
        }
    }

    private void insertProductFullReductionList(Long productId, List<PmsProductFullReduction> productFullReductionList) {
        if (!CollectionUtils.isEmpty(productFullReductionList)) {
            productFullReductionList.forEach(pmsProductFullReduction -> {
                pmsProductFullReduction.setProductId(productId);
                productFullReductionMapper.insert(pmsProductFullReduction);
            });
        }
    }

    private void insertProductLadderList(Long productId, List<PmsProductLadder> productLadderList) {
        if (!CollectionUtils.isEmpty(productLadderList)) {
            productLadderList.forEach(pmsProductLadder -> {
                pmsProductLadder.setProductId(productId);

            });
        }
    }

    private void insertMemberPriceList(Long productId, List<PmsMemberPrice> memberPriceList) {
        if (!CollectionUtils.isEmpty(memberPriceList)) {
            memberPriceList.forEach(memberPrice -> {
                memberPrice.setProductId(productId);
                //??????????????????
                memberPriceMapper.insert(memberPrice);
            });
        }
    }

    @Override
    public Integer updateProduct(Long id, PmsProductParam productParam) {
        //????????????????????????????????????????????????????????????
        productParam.setId(id);
        int count = mapper.updateByPrimaryKeySelective(productParam);

        //1 ?????????????????? ?????????????????????????????? ???????????????
        //delete from pms_member_price where product_id = ?
        PmsMemberPriceExample priceExample = new PmsMemberPriceExample();
        priceExample.createCriteria()
                .andProductIdEqualTo(id);
        memberPriceMapper.deleteByExample(priceExample);
        insertMemberPriceList(id, productParam.getMemberPriceList());

        //2 ??????????????????
        PmsProductLadderExample productLadderExample = new PmsProductLadderExample();
        productLadderExample.createCriteria().andProductIdEqualTo(id);
        productLadderMapper.deleteByExample(productLadderExample);
        insertProductLadderList(id, productParam.getProductLadderList());

        //3 ??????????????????
        PmsProductFullReductionExample productFullReductionExample = new PmsProductFullReductionExample();
        productFullReductionExample.createCriteria().andProductIdEqualTo(id);
        productFullReductionMapper.deleteByExample(productFullReductionExample);
        insertProductFullReductionList(id, productParam.getProductFullReductionList());

        //4 ??????????????????
        PmsProductAttributeValueExample productAttributeValueExample = new PmsProductAttributeValueExample();
        productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        productAttributeValueMapper.deleteByExample(productAttributeValueExample);
        insertProductAttributeValueList(id, productParam.getProductAttributeValueList());

        //5 ??????????????????
        CmsSubjectProductRelationExample subjectProductRelationExample = new CmsSubjectProductRelationExample();
        subjectProductRelationExample.createCriteria()
                .andProductIdEqualTo(id);
        subjectProductRelationMapper.deleteByExample(subjectProductRelationExample);
        insertSubjectProductRelationList(id, productParam.getSubjectProductRelationList());

        //6 ??????????????????
        CmsPrefrenceAreaProductRelationExample prefrenceAreaProductRelationExample = new CmsPrefrenceAreaProductRelationExample();
        prefrenceAreaProductRelationExample.createCriteria().andProductIdEqualTo(id);
        productRelationMapper.deleteByExample(prefrenceAreaProductRelationExample);
        insertPrefrenceAreaProductRelationList(id, productParam.getPrefrenceAreaProductRelationList());

        //7 ??????sku
        List<PmsSkuStock> skuStockList = productParam.getSkuStockList();
        // a ??????????????????sku????????????????????????????????????????????????sku????????????????????????sku??????
        if (CollectionUtils.isEmpty(skuStockList)) {
            //delete  from pms_sku_stock where product_id = ?
            PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
            skuStockExample.createCriteria().andProductIdEqualTo(id);
            skuStockMapper.deleteByExample(skuStockExample);
        } else {
            // b ?????????????????????????????????3??????????????? ?????????  ?????????
            // ????????????????????????????????????????????????????????????sku
            // select * from pms_sku_stock where product_id = ?
            PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
            skuStockExample.createCriteria().andProductIdEqualTo(id);
            List<PmsSkuStock> oldPmsSkuStock = skuStockMapper.selectByExample(skuStockExample);

            List<PmsSkuStock> updateSkuStockList = new ArrayList<>();
            List<PmsSkuStock> insertSkuStockList = new ArrayList<>();
            for (PmsSkuStock pmsSkuStock : skuStockList) {
                if (pmsSkuStock.getId() == null) {
                    //?????????
                    insertSkuStockList.add(pmsSkuStock);
                    continue;
                }
                for (PmsSkuStock oldSkuStock : oldPmsSkuStock) {
                    if (pmsSkuStock.getId().equals(oldSkuStock.getId())) {
                        //????????????
                        updateSkuStockList.add(pmsSkuStock);
                    }
                }
            }

            List<Long> deleteSkuStockList = new ArrayList<>();

            for (PmsSkuStock oldSkuStock : oldPmsSkuStock) {

                //??????oldSkuStock ????????????
                boolean contains = false;
                for (PmsSkuStock pmsSkuStock : updateSkuStockList) {
                    if (pmsSkuStock.getId().equals(oldSkuStock.getId())) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    deleteSkuStockList.add(oldSkuStock.getId());
                }
            }

            //????????????3?????????
            insertSkuStockList(id, insertSkuStockList);


            //?????????????????????
            handleSkuCode(id,updateSkuStockList);
            for (PmsSkuStock pmsSkuStock : updateSkuStockList) {
                skuStockMapper.updateByPrimaryKeySelective(pmsSkuStock);
            }

            PmsSkuStockExample deleteSkuStockExample = new PmsSkuStockExample();
            deleteSkuStockExample.createCriteria()
                    .andIdIn(deleteSkuStockList);
            skuStockMapper.deleteByExample(deleteSkuStockExample);

        }

        return count;
    }

    @Resource
    private PmsProductDao productDao;

    @Override
    public PmsProductResult getUpdateInfoById(Long id) {
        return productDao.selectUpdateInfoById(id);
    }

    @Override
    public Integer updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PmsProductExample example = new PmsProductExample();
        example.createCriteria()
                .andIdIn(ids);

        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(verifyStatus);

        int count = mapper.updateByExampleSelective(product, example);
        if (count > 0) {
            //???????????????????????? ????????????
            for (Long id : ids) {
                PmsProductVertifyRecord vertifyRecord = new PmsProductVertifyRecord();
                vertifyRecord.setVertifyMan("admin");
                vertifyRecord.setStatus(verifyStatus);
                vertifyRecord.setCreateTime(new Date());
                vertifyRecord.setProductId(id);
                vertifyRecord.setDetail(detail);

                productVertifyRecordMapper.insert(vertifyRecord);
            }
        }

        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<PmsProduct> listProduct(String keyword) {
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        // select * from pms_product where name like '%?%' or product_sn like '%?%'
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
            example.or().andProductSnLike("%" + keyword + "%");
        }
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<PmsProduct> pageListProduct(PmsProductDTO productDTO, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        // ??????where??????
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(productDTO.getProductSn())) {
            criteria.andProductSnLike("%" + productDTO.getProductSn() + "%");
        }
        if (productDTO.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productDTO.getProductCategoryId());
        }
        if (productDTO.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productDTO.getBrandId());
        }
        if (!StringUtils.isEmpty(productDTO.getKeyword())) {
            criteria.andNameLike("%" + productDTO.getKeyword() + "%");
        }
        if (productDTO.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(productDTO.getPublishStatus());
        }

        if (productDTO.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productDTO.getVerifyStatus());
        }

        return new PageInfo<>(mapper.selectByExample(example));
    }

}
