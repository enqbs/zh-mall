package com.enqbs.admin.form;

public class ProductForm {

    private Integer productCategoryId;

    private String title;

    private String subTitle;

    private String picture;

    private String slider;

    private String lowestPrice;

    private String virtualPrice;

    private Integer sale;

    private Integer saleableStatus;

    private Integer newStatus;

    private Integer recommendStatus;

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

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(String virtualPrice) {
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

}
