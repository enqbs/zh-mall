package com.enqbs.pay.enums;

import lombok.Getter;

@Getter
public enum PayPlatformEnum {

    ALI_PAY(1, "AliPay"),

    WE_CHAT_PAY(2, "WeChatPay");
    
    private final Integer code;
    
    private final String desc;

    PayPlatformEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
}
