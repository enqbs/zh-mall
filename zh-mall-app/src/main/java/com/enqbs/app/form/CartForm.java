package com.enqbs.app.form;

import javax.validation.constraints.NotNull;

public class CartForm {

    @NotNull(message = "商品不能为空")
    private Integer productId;

    @NotNull(message = "商品规格不能为空")
    private Integer skuId;

    @NotNull(message = "数量不能为空")
    private Integer quantity;

    private Boolean selected = true;

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

}
