package com.enqbs.admin.vo;

import java.io.Serializable;

public class OrderShippingAddressVO implements Serializable {

    private Long orderNo;

    private String name;

    private String telNo;

    private String address;

    private String detailAddress;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

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
