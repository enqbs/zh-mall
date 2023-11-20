package com.enqbs.admin.vo;

import java.io.Serializable;

public class OrderLogisticsInfoVO implements Serializable {

    private Long orderNo;

    private String logisticsNo;

    private String logisticsTitle;

    private Integer deleteStatus;

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
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsTitle() {
        return logisticsTitle;
    }

    public void setLogisticsTitle(String logisticsTitle) {
        this.logisticsTitle = logisticsTitle;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "OrderLogisticsInfoVO{" +
                "orderNo=" + orderNo +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", logisticsTitle='" + logisticsTitle + '\'' +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

}
