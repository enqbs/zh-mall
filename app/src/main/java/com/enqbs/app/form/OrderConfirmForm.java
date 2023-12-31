package com.enqbs.app.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderConfirmForm {

    @NotBlank(message = "订单凭证不能为空")
    private String orderToken;

    @NotNull(message = "请选择优惠券")
    private Integer couponsId;

}
