package com.enqbs.admin.vo;

import java.io.Serializable;
import java.util.Date;

public class SkuStockVO implements Serializable {

    private Integer id;

    private Integer skuId;

    private Integer actualStock;

    private Integer lockStock;

    private Integer stock;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
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

    @Override
    public String toString() {
        return "SkuStockVO{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", actualStock=" + actualStock +
                ", lockStock=" + lockStock +
                ", stock=" + stock +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
