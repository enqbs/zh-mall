package com.enqbs.pay.enums;

import lombok.Getter;

@Getter
public enum PayTypeEnum {

    ALIPAY_PC("FAST_INSTANT_TRADE_PAY", PayPlatformEnum.ALI_PAY.getDesc(), "支付宝电脑网站支付"),

    ALIPAY_WAP("QUICK_WAP_WAY", PayPlatformEnum.ALI_PAY.getDesc(), "支付宝手机网站支付"),

    ALIPAY_APP("QUICK_MSECURITY_PAY", PayPlatformEnum.ALI_PAY.getDesc(), "支付宝APP支付"),

    WE_CHAT_PAY_NATIVE("native", PayPlatformEnum.WE_CHAT_PAY.getDesc(), "微信Native支付");

    private final String payType;

    private final String payPlatform;

    private final String desc;

    PayTypeEnum(String payType, String payPlatform, String desc) {
        this.payType = payType;
        this.payPlatform = payPlatform;
        this.desc = desc;
    }

}
