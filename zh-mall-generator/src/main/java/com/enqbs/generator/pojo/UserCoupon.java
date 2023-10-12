package com.enqbs.generator.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserCoupon implements Serializable {

    private Integer couponId;

    private Integer userId;

    private Integer quantity;

    private Integer status;

    private Integer consumeVersion;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        return "UserCoupon{" +
                "couponId=" + couponId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", status=" + status +
                ", consumeVersion=" + consumeVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
