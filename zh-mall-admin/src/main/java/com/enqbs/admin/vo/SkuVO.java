package com.enqbs.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SkuVO implements Serializable {

    private Integer id;

    private Integer productId;

    private String picture;

    private String title;

    private String param;

    private BigDecimal price;

    private Integer saleableStatus;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    private SkuStockVO skuStock;

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

    public Integer getSaleableStatus() {
        return saleableStatus;
    }

    public void setSaleableStatus(Integer saleableStatus) {
        this.saleableStatus = saleableStatus;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public SkuStockVO getSkuStock() {
        return skuStock;
    }

    public void setSkuStock(SkuStockVO skuStock) {
        this.skuStock = skuStock;
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
                ", saleableStatus=" + saleableStatus +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", skuStock=" + skuStock +
                '}';
    }

}
