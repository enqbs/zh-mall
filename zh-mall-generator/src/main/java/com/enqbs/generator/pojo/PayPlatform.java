package com.enqbs.generator.pojo;

import java.io.Serializable;

public class PayPlatform implements Serializable {

    private Long payInfoId;

    private Long orderNo;

    private String payType;

    private Integer platform;

    private String platformNumber;

    private Integer sharding;

    private static final long serialVersionUID = 1L;

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
        this.payType = payType == null ? null : payType.trim();
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber == null ? null : platformNumber.trim();
    }

    public Integer getSharding() {
        return sharding;
    }

    public void setSharding(Integer sharding) {
        this.sharding = sharding;
    }

    @Override
    public String toString() {
        return "PayPlatform{" +
                "payInfoId=" + payInfoId +
                ", orderNo=" + orderNo +
                ", payType='" + payType + '\'' +
                ", platform=" + platform +
                ", platformNumber='" + platformNumber + '\'' +
                ", sharding=" + sharding +
                '}';
    }

}
