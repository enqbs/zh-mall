package com.enqbs.app.pojo.vo;

import java.io.Serializable;
import java.util.Date;

public class UserCouponVO implements Serializable {

    private Integer id;

    private Integer couponId;

    private Integer userId;

    private Integer quantity;

    private Integer status;

    private Date createTime;

    private CouponVO coupon;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CouponVO getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponVO coupon) {
        this.coupon = coupon;
    }

    @Override
    public String toString() {
        return "UserCouponVO{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", status=" + status +
                ", createTime=" + createTime +
                ", coupon=" + coupon +
                '}';
    }

}
