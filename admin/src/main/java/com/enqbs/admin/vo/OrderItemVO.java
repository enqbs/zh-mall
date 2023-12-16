package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemVO implements Serializable {

    private Long orderNo;

    private Integer skuId;

    private Integer productId;

    private String skuTitle;

    private String skuParam;

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
                ", productId=" + productId +
                ", skuTitle='" + skuTitle + '\'' +
                ", skuParam='" + skuParam + '\'' +
                ", skuPicture='" + skuPicture + '\'' +
                ", num=" + num +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", actualPrice=" + actualPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
