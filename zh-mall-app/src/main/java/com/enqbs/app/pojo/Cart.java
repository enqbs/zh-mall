package com.enqbs.app.pojo;

import java.io.Serializable;

public class Cart implements Serializable {

    private Integer productId;;

    private Integer skuId;

    private Integer quantity;

    private Boolean selected;

    public Cart() {
    }

    public Cart(Integer productId, Integer skuId, Integer quantity, Boolean selected) {
        this.productId = productId;
        this.skuId = skuId;
        this.quantity = quantity;
        this.selected = selected;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productId=" + productId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", selected=" + selected +
                '}';
    }

}
