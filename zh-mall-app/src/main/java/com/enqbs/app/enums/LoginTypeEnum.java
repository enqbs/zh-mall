package com.enqbs.app.enums;

public enum LoginTypeEnum {

    USERNAME("username", null),

    PHONE("phone", null),

    EMAIL("Email", null),

    ALIPAY_PC("AliPay", "alipay_pc");

    private final String identityType;

    private final String identifier;

    public String getIdentityType() {
        return identityType;
    }

    public String getIdentifier() {
        return identifier;
    }

    LoginTypeEnum(String identityType, String identifier) {
        this.identityType = identityType;
        this.identifier = identifier;
    }

}
