package com.enqbs.generator.pojo;

import java.io.Serializable;

public class OrderShippingAddress implements Serializable {

    private Long orderNo;

    private String name;

    private String telNo;

    private String address;

    private String detailAddress;

    private Integer sharding;

    private static final long serialVersionUID = 1L;

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
        this.name = name == null ? null : name.trim();
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo == null ? null : telNo.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    public Integer getSharding() {
        return sharding;
    }

    public void setSharding(Integer sharding) {
        this.sharding = sharding;
    }

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
