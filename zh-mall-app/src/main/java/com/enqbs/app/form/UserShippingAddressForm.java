package com.enqbs.app.form;

import javax.validation.constraints.NotBlank;

public class UserShippingAddressForm {

    @NotBlank(message = "收货人名称不能为空")
    private String name;

    @NotBlank(message = "收货人联系方式不能为空")
    private String telNo;

    @NotBlank(message = "收货人地址不能为空")
    private String address;

    @NotBlank(message = "收货人详细地址不能为空")
    private String detailAddress;

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

}
