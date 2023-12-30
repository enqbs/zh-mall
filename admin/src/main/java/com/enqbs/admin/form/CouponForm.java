package com.enqbs.admin.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
