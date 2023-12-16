package com.enqbs.admin.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    TIMEOUT(-1, "订单超时"),

    NOT_PAY(0, "未支付"),

    PAY_SUCCESS(1, "已支付"),

    NOT_RECEIPT(2, "待收货"),

    RECEIPT_SUCCESS(3, "已收货");

    private final Integer code;

    private final String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
