package com.enqbs.app.vo;

import java.math.BigDecimal;

public class CartProductVO {

    private Integer productId;

    private Integer skuId;

    private String picture;             // 商品规格图片

    private String productTitle;        // 商品标题

    private String productSubTitle;     // 商品副标题

    private String skuTitle;            // 商品规格标题

    private String param;               // 商品规格属性

    private Integer quantity;           // 数量

    private BigDecimal price;           // 单价

    private BigDecimal totalPrice;      // 总价

    private Integer saleableStatus;     // 商品是否有效

    private Boolean selected;           // 是否选中

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getSaleableStatus() {
        return saleableStatus;
    }

    public void setSaleableStatus(Integer saleableStatus) {
        this.saleableStatus = saleableStatus;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "CartProductVO{" +
                "productId=" + productId +
                ", skuId=" + skuId +
                ", picture='" + picture + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", productSubTitle='" + productSubTitle + '\'' +
                ", skuTitle='" + skuTitle + '\'' +
                ", param='" + param + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", saleableStatus=" + saleableStatus +
                ", selected=" + selected +
                '}';
    }

}
