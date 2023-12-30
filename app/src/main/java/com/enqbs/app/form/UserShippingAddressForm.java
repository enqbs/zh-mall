package com.enqbs.app.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserShippingAddressForm {

    @NotBlank(message = "收货人名称不能为空")
    private String name;

    @NotBlank(message = "收货人联系方式不能为空")
    private String telNo;

    @NotBlank(message = "收货人地址不能为空")
    private String address;

    @NotBlank(message = "收货人详细地址不能为空")
    private String detailAddress;

}
