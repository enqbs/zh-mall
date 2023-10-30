package com.enqbs.admin.form;

import javax.validation.constraints.NotBlank;

public class LogisticsInfoForm {

    @NotBlank(message = "快递运单号不能为空")
    private String logisticsNo;

    @NotBlank(message = "快递公司名称不能为空")
    private String logisticsTitle;

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsTitle() {
        return logisticsTitle;
    }

    public void setLogisticsTitle(String logisticsTitle) {
        this.logisticsTitle = logisticsTitle;
    }

}
