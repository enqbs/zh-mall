package com.enqbs.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SkuVO implements Serializable {

    private Integer id;

    private Integer productId;

    private String picture;

    private String title;

    private String param;

    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SkuVO{" +
                "id=" + id +
                ", productId=" + productId +
                ", picture='" + picture + '\'' +
                ", title='" + title + '\'' +
                ", param='" + param + '\'' +
                ", price=" + price +
                '}';
    }

}
