package com.enqbs.app.pojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SkuStockDTO implements Serializable {

    private Integer skuId;

    private Integer spuId;

    private Integer skuStockId;

    private Integer actualStock;

    private Integer lockStock;

    private Integer stock;

    private Integer quantity;

    @Override
    public String toString() {
        return "SkuStockDTO{" +
                "skuId=" + skuId +
                ", spuId=" + spuId +
                ", skuStockId=" + skuStockId +
                ", actualStock=" + actualStock +
                ", lockStock=" + lockStock +
                ", stock=" + stock +
                ", quantity=" + quantity +
                '}';
    }

}
