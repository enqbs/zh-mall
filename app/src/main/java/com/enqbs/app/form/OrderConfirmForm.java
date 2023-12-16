package com.enqbs.app.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OrderConfirmForm {

    @NotBlank(message = "订单凭证不能为空")
    private String orderToken;

    private Integer couponsId;

}
