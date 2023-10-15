package com.enqbs.generator.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem implements Serializable {

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

    private Integer sharding;

    private static final long serialVersionUID = 1L;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

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

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle == null ? null : skuTitle.trim();
    }

    public String getSkuParam() {
        return skuParam;
    }

    public void setSkuParam(String skuParam) {
        this.skuParam = skuParam;
    }

    public String getSkuPicture() {
        return skuPicture;
    }

    public void setSkuPicture(String skuPicture) {
        this.skuPicture = skuPicture == null ? null : skuPicture.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getSharding() {
        return sharding;
    }

    public void setSharding(Integer sharding) {
        this.sharding = sharding;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
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
                ", sharding=" + sharding +
                '}';
    }

}
