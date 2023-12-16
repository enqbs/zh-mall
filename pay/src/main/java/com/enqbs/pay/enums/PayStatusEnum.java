package com.enqbs.pay.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {

    NOT_PAY(0, "未支付"),

    PAY_SUCCESS(1, "支付成功"),

    PAY_TIMEOUT(2, "超时");

    private final Integer code;

    private final String desc;

    PayStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
