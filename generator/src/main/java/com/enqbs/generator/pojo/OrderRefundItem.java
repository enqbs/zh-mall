package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderRefundItem implements Serializable {

    private Long orderNo;

    private Integer skuId;

    private Integer spuId;

    private String skuTitle;

    private String skuParams;

    private String skuPicture;

    private Integer num;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private BigDecimal actualPrice;

    private BigDecimal totalPrice;

    private BigDecimal refundAmount;

    private Integer sharding;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "OrderRefundItem{" +
                "orderNo=" + orderNo +
                ", skuId=" + skuId +
                ", spuId=" + spuId +
                ", skuTitle='" + skuTitle + '\'' +
                ", skuParams='" + skuParams + '\'' +
                ", skuPicture='" + skuPicture + '\'' +
                ", num=" + num +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", actualPrice=" + actualPrice +
                ", totalPrice=" + totalPrice +
                ", refundAmount=" + refundAmount +
                ", sharding=" + sharding +
                '}';
    }

}
