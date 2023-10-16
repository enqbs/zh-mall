package com.enqbs.app.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderForm {

    @NotBlank(message = "订单凭证不能为空")
    private String orderToken;

    @NotNull(message = "请选择收货地址")
    private Integer shippingAddressId;

    private Integer couponsId;

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    public Integer getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Integer shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

}
