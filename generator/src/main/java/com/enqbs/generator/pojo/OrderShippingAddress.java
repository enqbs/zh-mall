package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class OrderShippingAddress implements Serializable {

    private Long orderNo;

    private String name;

    private String telNo;

    private String address;

    private String detailAddress;

    private Integer sharding;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "OrderShippingAddress{" +
                "orderNo=" + orderNo +
                ", name='" + name + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", sharding=" + sharding +
                '}';
    }

}
