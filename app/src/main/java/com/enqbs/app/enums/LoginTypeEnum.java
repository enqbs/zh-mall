package com.enqbs.app.enums;

import lombok.Getter;

@Getter
public enum LoginTypeEnum {

    USERNAME("username", null),

    PHONE("phone", null),

    EMAIL("Email", null),

    ALIPAY_PC("AliPay", "alipay_pc");

    private final String identityType;

    private final String identifier;

    LoginTypeEnum(String identityType, String identifier) {
        this.identityType = identityType;
        this.identifier = identifier;
    }

}
