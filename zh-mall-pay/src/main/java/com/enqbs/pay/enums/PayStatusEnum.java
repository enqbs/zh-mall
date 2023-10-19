package com.enqbs.pay.enums;

public enum PayStatusEnum {

    NOT_PAY(0, "未支付"),

    PAY_SUCCESS(1, "支付成功"),

    PAY_TIMEOUT(2, "超时");

    private final Integer code;

    private final String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    PayStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
