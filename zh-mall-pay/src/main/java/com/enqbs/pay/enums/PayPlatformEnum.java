package com.enqbs.pay.enums;

public enum PayPlatformEnum {

    ALI_PAY(1, "AliPay"),

    WE_CHAT_PAY(2, "WeChatPay");
    
    private final Integer code;
    
    private final String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    PayPlatformEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
}
