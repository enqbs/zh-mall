package com.enqbs.admin.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CouponForm {

    private Integer productId;

    @NotBlank(message = "优惠金额不能为空")
    private String denomination;

    @NotBlank(message = "优惠条件金额不能为空")
    private String condition;

    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @NotBlank(message = "优惠券起始时间不能为空")
    private String startDate;

    @NotBlank(message = "优惠券结束时间不能为空")
    private String endDate;

    private Integer status;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
