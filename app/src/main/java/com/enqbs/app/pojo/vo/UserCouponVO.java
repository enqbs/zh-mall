package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserCouponVO implements Serializable {

    private Integer id;

    private Integer couponId;

    private Integer userId;

    private Integer quantity;

    private Integer status;

    private Date createTime;

    private CouponVO coupon;

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
