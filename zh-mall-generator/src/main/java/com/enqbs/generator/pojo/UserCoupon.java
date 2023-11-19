package com.enqbs.generator.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserCoupon implements Serializable {

    private Integer id;

    private Integer couponId;

    private Integer userId;

    private Integer quantity;

    private Integer status;

    private Integer deleteStatus;

    private Integer consumeVersion;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
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
                "id=" + id +
                ", couponId=" + couponId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", consumeVersion=" + consumeVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
