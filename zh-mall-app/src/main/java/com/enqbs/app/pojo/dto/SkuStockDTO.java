package com.enqbs.app.pojo.dto;

import java.io.Serializable;

public class SkuStockDTO implements Serializable {

    private Integer skuId;

    private Integer productId;

    private Integer skuStockId;

    private Integer actualStock;

    private Integer lockStock;

    private Integer stock;

    private Integer quantity;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSkuStockId() {
        return skuStockId;
    }

    public void setSkuStockId(Integer skuStockId) {
        this.skuStockId = skuStockId;
    }

    public Integer getActualStock() {
        return actualStock;
    }

    public void setActualStock(Integer actualStock) {
        this.actualStock = actualStock;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SkuStockDTO{" +
                "skuId=" + skuId +
                ", productId=" + productId +
                ", skuStockId=" + skuStockId +
                ", actualStock=" + actualStock +
                ", lockStock=" + lockStock +
                ", stock=" + stock +
                ", quantity=" + quantity +
                '}';
    }

}
