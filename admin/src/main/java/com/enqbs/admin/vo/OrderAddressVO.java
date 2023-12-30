package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderAddressVO implements Serializable {

    private Long orderNo;

    private String name;

    private String telNo;

    private String address;

    private String detailAddress;

    @Override
    public String toString() {
        return "OrderAddressVO{" +
                "orderNo=" + orderNo +
                ", name='" + name + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }

}
