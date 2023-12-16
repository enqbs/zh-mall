package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderLogisticsInfo implements Serializable {

    private Long orderNo;

    private String logisticsNo;

    private String logisticsTitle;

    private Integer deleteStatus;

    private Integer sharding;

    private static final long serialVersionUID = 1L;

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
