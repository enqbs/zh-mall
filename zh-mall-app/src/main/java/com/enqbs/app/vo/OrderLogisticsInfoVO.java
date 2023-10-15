package com.enqbs.app.vo;

import java.io.Serializable;

public class OrderLogisticsInfoVO implements Serializable {

    private Long orderNo;

    private String logisticsNo;

    private String logisticsTitle;

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

    @Override
    public String toString() {
        return "OrderLogisticsInfoVO{" +
                "orderNo=" + orderNo +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", logisticsTitle='" + logisticsTitle + '\'' +
                '}';
    }

}
