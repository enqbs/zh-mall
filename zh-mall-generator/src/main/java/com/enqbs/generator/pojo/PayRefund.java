package com.enqbs.generator.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayRefund implements Serializable {

    private Long id;

    private Long payInfoId;

    private Long orderNo;

    private Integer platform;

    private String platformNo;

    private BigDecimal payAmount;

    private BigDecimal refundAmount;

    private Integer deleteStatus;

    private Integer consumeVersion;

    private Integer sharding;

    private Date createTime;

    private Date refundTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
        this.platformNo = platformNo == null ? null : platformNo.trim();
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

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getConsumeVersion() {
        return consumeVersion;
    }

    public void setConsumeVersion(Integer consumeVersion) {
        this.consumeVersion = consumeVersion;
    }

    public Integer getSharding() {
        return sharding;
    }

    public void setSharding(Integer sharding) {
        this.sharding = sharding;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PayRefund{" +
                "id=" + id +
                ", payInfoId=" + payInfoId +
                ", orderNo=" + orderNo +
                ", platform=" + platform +
                ", platformNo='" + platformNo + '\'' +
                ", payAmount=" + payAmount +
                ", refundAmount=" + refundAmount +
                ", deleteStatus=" + deleteStatus +
                ", consumeVersion=" + consumeVersion +
                ", sharding=" + sharding +
                ", createTime=" + createTime +
                ", refundTime=" + refundTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
