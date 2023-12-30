package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Order implements Serializable {

    private Long id;

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

    private Integer deleteStatus;

    private Integer consumeVersion;

    private Integer sharding;

    private Date createTime;

    private Date paymentTime;

    private Date shipTime;

    private Date signReceiptTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNo=" + orderNo +
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
                ", deleteStatus=" + deleteStatus +
                ", consumeVersion=" + consumeVersion +
                ", sharding=" + sharding +
                ", createTime=" + createTime +
                ", paymentTime=" + paymentTime +
                ", shipTime=" + shipTime +
                ", signReceiptTime=" + signReceiptTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
