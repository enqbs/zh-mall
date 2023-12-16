package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PayPlatformVO implements Serializable {

    private Long payInfoId;

    private Long orderNo;

    private String payType;

    private String platform;

    private String platformNo;

    @Override
    public String toString() {
        return "PayPlatformVO{" +
                "payInfoId=" + payInfoId +
                ", orderNo=" + orderNo +
                ", payType='" + payType + '\'' +
                ", platform='" + platform + '\'' +
                ", platformNo='" + platformNo + '\'' +
                '}';
    }

}
