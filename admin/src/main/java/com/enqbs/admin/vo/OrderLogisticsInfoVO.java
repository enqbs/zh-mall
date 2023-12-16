package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderLogisticsInfoVO implements Serializable {

    private Long orderNo;

    private String logisticsNo;

    private String logisticsTitle;

    private Integer deleteStatus;

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
