package com.baizhi.mall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OmsOrderItem implements Serializable {
    private Long id;

    private Long orderId;

    private String orderSn;

    private Long productId;

    private String productPic;

    private String productName;

    private String productBrand;

    private String productSn;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private Long productSkuId;

    private String productSkuCode;

    private Long productCategoryId;

    private String promotionName;

    private BigDecimal promotionAmount;

    private BigDecimal couponAmount;

    private BigDecimal integrationAmount;

    private BigDecimal realAmount;

    private Integer giftIntegration;

    private Integer giftGrowth;

    private String productAttr;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", productId=").append(productId);
        sb.append(", productPic=").append(productPic);
        sb.append(", productName=").append(productName);
        sb.append(", productBrand=").append(productBrand);
        sb.append(", productSn=").append(productSn);
        sb.append(", productPrice=").append(productPrice);
        sb.append(", productQuantity=").append(productQuantity);
        sb.append(", productSkuId=").append(productSkuId);
        sb.append(", productSkuCode=").append(productSkuCode);
        sb.append(", productCategoryId=").append(productCategoryId);
        sb.append(", promotionName=").append(promotionName);
        sb.append(", promotionAmount=").append(promotionAmount);
        sb.append(", couponAmount=").append(couponAmount);
        sb.append(", integrationAmount=").append(integrationAmount);
        sb.append(", realAmount=").append(realAmount);
        sb.append(", giftIntegration=").append(giftIntegration);
        sb.append(", giftGrowth=").append(giftGrowth);
        sb.append(", productAttr=").append(productAttr);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}