package com.enqbs.admin.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SkuForm {

    @NotNull(message = "商品ID不能为空")
    private Integer productId;

    private String picture;

    private String title;

    private String param;

    @NotBlank(message = "价格不能为空")
    private String price;

    @NotNull(message = "库存数量不能为空")
    private Integer stock;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
