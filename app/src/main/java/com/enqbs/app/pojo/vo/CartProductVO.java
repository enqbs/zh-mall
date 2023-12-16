package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartProductVO implements Serializable {

    private Integer spuId;

    private Integer skuId;

    private String picture;             // 商品规格图片

    private String productTitle;        // 商品标题

    private String productSubTitle;     // 商品副标题

    private String skuTitle;            // 商品规格标题

    private List<SkuParamVO> params;    // 商品规格属性

    private Integer quantity;           // 数量

    private BigDecimal price;           // 单价

    private BigDecimal totalPrice;      // 总价

    private Integer saleableStatus;     // 商品是否有效

    private Boolean selected;           // 是否选中

    @Override
    public String toString() {
        return "CartProductVO{" +
                "spuId=" + spuId +
                ", skuId=" + skuId +
                ", picture='" + picture + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", productSubTitle='" + productSubTitle + '\'' +
                ", skuTitle='" + skuTitle + '\'' +
                ", params=" + params +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", saleableStatus=" + saleableStatus +
                ", selected=" + selected +
                '}';
    }

}
