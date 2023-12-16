package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderLogisticsInfoVO implements Serializable {

    private Long orderNo;

    private String logisticsNo;

    private String logisticsTitle;

    @Override
    public String toString() {
        return "OrderLogisticsInfoVO{" +
                "orderNo=" + orderNo +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", logisticsTitle='" + logisticsTitle + '\'' +
                '}';
    }

}
