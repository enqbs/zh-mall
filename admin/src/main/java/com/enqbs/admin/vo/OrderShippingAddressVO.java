package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderShippingAddressVO implements Serializable {

    private Long orderNo;

    private String name;

    private String telNo;

    private String address;

    private String detailAddress;

    @Override
    public String toString() {
        return "OrderShippingAddressVO{" +
                "orderNo=" + orderNo +
                ", name='" + name + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }

}
