package com.enqbs.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderConfirmVO implements Serializable {

    private List<UserShippingAddressVO> shippingAddressVOList;

    private List<OrderItemVO> orderItemVOList;

    private BigDecimal postage;

    private BigDecimal amount;

    private BigDecimal couponAmount;

    private BigDecimal discountAmount;

    private BigDecimal actualAmount;

    private String orderToken;

    public List<UserShippingAddressVO> getShippingAddressVOList() {
        return shippingAddressVOList;
    }

    public void setShippingAddressVOList(List<UserShippingAddressVO> shippingAddressVOList) {
        this.shippingAddressVOList = shippingAddressVOList;
    }

    public List<OrderItemVO> getOrderItemVOList() {
        return orderItemVOList;
    }

    public void setOrderItemVOList(List<OrderItemVO> orderItemVOList) {
        this.orderItemVOList = orderItemVOList;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    @Override
    public String toString() {
        return "OrderConfirmVO{" +
                "shippingAddressVOList=" + shippingAddressVOList +
                ", orderItemVOList=" + orderItemVOList +
                ", postage=" + postage +
                ", amount=" + amount +
                ", couponAmount=" + couponAmount +
                ", discountAmount=" + discountAmount +
                ", actualAmount=" + actualAmount +
                ", orderToken='" + orderToken + '\'' +
                '}';
    }

}
