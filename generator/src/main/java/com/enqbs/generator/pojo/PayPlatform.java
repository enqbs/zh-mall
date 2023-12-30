package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class PayPlatform implements Serializable {

    private Long payInfoId;

    private Long orderNo;

    private String payType;

    private String platform;

    private String platformNo;

    private Integer sharding;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "PayPlatform{" +
                "payInfoId=" + payInfoId +
                ", orderNo=" + orderNo +
                ", payType='" + payType + '\'' +
                ", platform='" + platform + '\'' +
                ", platformNo='" + platformNo + '\'' +
                ", sharding=" + sharding +
                '}';
    }

}
