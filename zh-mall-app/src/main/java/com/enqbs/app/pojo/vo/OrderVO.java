package com.enqbs.app.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderSc() {
        return orderSc;
    }

    public void setOrderSc(String orderSc) {
        this.orderSc = orderSc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
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

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public Date getSignReceiptTime() {
        return signReceiptTime;
    }

    public void setSignReceiptTime(Date signReceiptTime) {
        this.signReceiptTime = signReceiptTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public OrderShippingAddressVO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(OrderShippingAddressVO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderLogisticsInfoVO getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(OrderLogisticsInfoVO logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }

    public List<OrderItemVO> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemVO> orderItemList) {
        this.orderItemList = orderItemList;
    }

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
