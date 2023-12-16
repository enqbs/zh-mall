package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderItemVO implements Serializable {

    private Long orderNo;

    private Integer skuId;

    private Integer spuId;

    private String skuTitle;

    private List<SkuParamVO> skuParams;

    private String skuPicture;

    private Integer num;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private BigDecimal actualPrice;

    private BigDecimal totalPrice;

    @Override
    public String toString() {
        return "OrderItemVO{" +
                "orderNo=" + orderNo +
                ", skuId=" + skuId +
                ", spuId=" + spuId +
                ", skuTitle='" + skuTitle + '\'' +
                ", skuParams=" + skuParams +
                ", skuPicture='" + skuPicture + '\'' +
                ", num=" + num +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", actualPrice=" + actualPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
