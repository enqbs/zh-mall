package com.enqbs.app.vo;

import java.math.BigDecimal;
import java.util.List;

public class ProductVO {

    private Integer id;

    private Integer productCategoryId;

    private String title;

    private String subTitle;

    private String picture;

    private String slider;

    private BigDecimal lowestPrice;

    private BigDecimal virtualPrice;

    private Integer sale;

    private Integer saleableStatus;

    private Integer newStatus;

    private Integer recommendStatus;

    private List<SkuVO> skuList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSlider() {
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public BigDecimal getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(BigDecimal virtualPrice) {
        this.virtualPrice = virtualPrice;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getSaleableStatus() {
        return saleableStatus;
    }

    public void setSaleableStatus(Integer saleableStatus) {
        this.saleableStatus = saleableStatus;
    }

    public Integer getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Integer newStatus) {
        this.newStatus = newStatus;
    }

    public Integer getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(Integer recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public List<SkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuVO> skuList) {
        this.skuList = skuList;
    }

    @Override
    public String toString() {
        return "ProductVO{" +
                "id=" + id +
                ", productCategoryId=" + productCategoryId +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", picture='" + picture + '\'' +
                ", slider='" + slider + '\'' +
                ", lowestPrice=" + lowestPrice +
                ", virtualPrice=" + virtualPrice +
                ", sale=" + sale +
                ", saleableStatus=" + saleableStatus +
                ", newStatus=" + newStatus +
                ", recommendStatus=" + recommendStatus +
                ", skuList=" + skuList +
                '}';
    }

}
