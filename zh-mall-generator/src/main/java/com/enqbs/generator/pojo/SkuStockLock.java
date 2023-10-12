package com.enqbs.generator.pojo;

import java.io.Serializable;
import java.util.Date;

public class SkuStockLock implements Serializable {

    private Long orderNo;

    private Integer skuId;

    private Integer productId;

    private Integer count;

    private Integer status;

    private Integer consumeVersion;

    private Date createTime;

    private Date updateTime;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getConsumeVersion() {
        return consumeVersion;
    }

    public void setConsumeVersion(Integer consumeVersion) {
        this.consumeVersion = consumeVersion;
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
        return "SkuStockLock{" +
                "orderNo=" + orderNo +
                ", skuId=" + skuId +
                ", productId=" + productId +
                ", count=" + count +
                ", status=" + status +
                ", consumeVersion=" + consumeVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
