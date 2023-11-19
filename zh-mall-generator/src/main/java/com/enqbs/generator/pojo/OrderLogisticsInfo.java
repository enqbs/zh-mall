package com.enqbs.generator.pojo;

import java.io.Serializable;

public class OrderLogisticsInfo implements Serializable {

    private Long orderNo;

    private String logisticsNo;

    private String logisticsTitle;

    private Integer deleteStatus;

    private Integer sharding;

    private static final long serialVersionUID = 1L;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public String getLogisticsTitle() {
        return logisticsTitle;
    }

    public void setLogisticsTitle(String logisticsTitle) {
        this.logisticsTitle = logisticsTitle == null ? null : logisticsTitle.trim();
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getSharding() {
        return sharding;
    }

    public void setSharding(Integer sharding) {
        this.sharding = sharding;
    }

    @Override
    public String toString() {
        return "OrderLogisticsInfo{" +
                "orderNo=" + orderNo +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", logisticsTitle='" + logisticsTitle + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", sharding=" + sharding +
                '}';
    }

}
