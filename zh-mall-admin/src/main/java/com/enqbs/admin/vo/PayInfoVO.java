package com.enqbs.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayInfoVO implements Serializable {

    private Long id;

    private Long orderNo;

    private Integer userId;

    private String nickName;

    private String photo;

    private BigDecimal payAmount;

    private Integer status;

    private Date createTime;

    private Date paymentTime;

    private Date updateTime;

    private PayPlatformVO payPlatform;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public PayPlatformVO getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(PayPlatformVO payPlatform) {
        this.payPlatform = payPlatform;
    }

    @Override
    public String toString() {
        return "PayInfoVO{" +
                "id=" + id +
                ", orderNo=" + orderNo +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", payAmount=" + payAmount +
                ", status=" + status +
                ", createTime=" + createTime +
                ", paymentTime=" + paymentTime +
                ", updateTime=" + updateTime +
                ", payPlatform=" + payPlatform +
                '}';
    }

}
