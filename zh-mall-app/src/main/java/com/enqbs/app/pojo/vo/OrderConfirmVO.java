package com.enqbs.app.pojo.vo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderConfirmVO implements Serializable {

    @Expose(serialize = false, deserialize = false)
    private List<UserShippingAddressVO> shippingAddressList;

    @Expose(serialize = false, deserialize = false)
    private List<UserCouponVO> couponList;

    private List<OrderItemVO> orderItemList;

    private Integer couponId;

    private BigDecimal postage;

    private BigDecimal amount;

    private BigDecimal couponAmount;

    private BigDecimal discountAmount;

    private BigDecimal actualAmount;

    private String orderToken;

    public List<UserShippingAddressVO> getShippingAddressList() {
        return shippingAddressList;
    }

    public void setShippingAddressList(List<UserShippingAddressVO> shippingAddressList) {
        this.shippingAddressList = shippingAddressList;
    }

    public List<UserCouponVO> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<UserCouponVO> couponList) {
        this.couponList = couponList;
    }

    public List<OrderItemVO> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemVO> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
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
