package com.enqbs.admin.vo;

import java.io.Serializable;

public class PayPlatformVO implements Serializable {

    private Long payInfoId;

    private Long orderNo;

    private String payType;

    private String platform;

    private String platformNumber;

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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    @Override
    public String toString() {
        return "PayPlatformVO{" +
                "payInfoId=" + payInfoId +
                ", orderNo=" + orderNo +
                ", payType='" + payType + '\'' +
                ", platform='" + platform + '\'' +
                ", platformNumber='" + platformNumber + '\'' +
                '}';
    }

}
