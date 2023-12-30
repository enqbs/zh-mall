package com.enqbs.app.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {

    @NotBlank(message = "订单凭证不能为空")
    private String orderToken;

    @NotNull(message = "请选择收货地址")
    private Integer shippingAddressId;

    private Integer couponId;

}
