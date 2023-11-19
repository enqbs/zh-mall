package com.enqbs.app.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayRefundVO implements Serializable {

    private Long id;

    private Long payInfoId;

    private Long orderNo;

    private Integer platform;

    private String platformNo;

    private BigDecimal payAmount;

    private BigDecimal refundAmount;

    private Date createTime;

    private Date refundTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPayInfoId() {
        return payInfoId;
    }

    public void setPayInfoId(Long payInfoId) {
        this.payInfoId = payInfoId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    @Override
    public String toString() {
        return "PayRefundVO{" +
                "id=" + id +
                ", payInfoId=" + payInfoId +
                ", orderNo=" + orderNo +
                ", platform=" + platform +
                ", platformNo='" + platformNo + '\'' +
                ", payAmount=" + payAmount +
                ", refundAmount=" + refundAmount +
                ", createTime=" + createTime +
                ", refundTime=" + refundTime +
                '}';
    }

}
