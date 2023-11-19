package com.enqbs.app.form;

import javax.validation.constraints.NotBlank;

public class OrderConfirmForm {

    @NotBlank(message = "订单凭证不能为空")
    private String orderToken;

    private Integer couponsId;

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

}
