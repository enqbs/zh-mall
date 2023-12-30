package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderConfirmVO implements Serializable {

    private transient List<UserAddressVO> shippingAddressList;

    private transient List<UserCouponVO> couponList;

    private List<OrderItemVO> orderItemList;

    private Integer couponId;

    private BigDecimal postage;

    private BigDecimal amount;

    private BigDecimal couponAmount;

    private BigDecimal discountAmount;

    private BigDecimal actualAmount;

    private String orderToken;

    @Override
    public String toString() {
        return "OrderConfirmVO{" +
                "shippingAddressList=" + shippingAddressList +
                ", couponList=" + couponList +
                ", orderItemList=" + orderItemList +
                ", couponId=" + couponId +
                ", postage=" + postage +
                ", amount=" + amount +
                ", couponAmount=" + couponAmount +
                ", discountAmount=" + discountAmount +
                ", actualAmount=" + actualAmount +
                ", orderToken='" + orderToken + '\'' +
                '}';
    }

}
