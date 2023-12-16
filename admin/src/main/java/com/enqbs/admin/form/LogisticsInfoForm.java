package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LogisticsInfoForm {

    @NotBlank(message = "快递运单号不能为空")
    private String logisticsNo;

    @NotBlank(message = "快递公司名称不能为空")
    private String logisticsTitle;

}
