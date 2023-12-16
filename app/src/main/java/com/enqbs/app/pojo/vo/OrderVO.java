package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderVO implements Serializable {

    private Long orderNo;

    private String orderSc;

    private Integer userId;

    private Integer couponId;

    private BigDecimal postage;

    private BigDecimal amount;

    private BigDecimal couponAmount;

    private BigDecimal discountAmount;

    private BigDecimal actualAmount;

    private Integer paymentType;

    private Integer status;

    private Date createTime;

    private Date paymentTime;

    private Date shipTime;

    private Date signReceiptTime;

    private Date updateTime;

    private OrderShippingAddressVO shippingAddress;

    private OrderLogisticsInfoVO logisticsInfo;

    private List<OrderItemVO> orderItemList;

    @Override
    public String toString() {
        return "OrderVO{" +
                "orderNo=" + orderNo +
                ", orderSc='" + orderSc + '\'' +
                ", userId=" + userId +
                ", couponId=" + couponId +
                ", postage=" + postage +
                ", amount=" + amount +
                ", couponAmount=" + couponAmount +
                ", discountAmount=" + discountAmount +
                ", actualAmount=" + actualAmount +
                ", paymentType=" + paymentType +
                ", status=" + status +
                ", createTime=" + createTime +
                ", paymentTime=" + paymentTime +
                ", shipTime=" + shipTime +
                ", signReceiptTime=" + signReceiptTime +
                ", updateTime=" + updateTime +
                ", shippingAddress=" + shippingAddress +
                ", logisticsInfo=" + logisticsInfo +
                ", orderItemList=" + orderItemList +
                '}';
    }

}
