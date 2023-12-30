package com.enqbs.app.service.pay;

import com.enqbs.pay.enums.PayStatusEnum;
import com.enqbs.pay.enums.PayTypeEnum;

import java.math.BigDecimal;

public interface PayInfoService {

    /*
     * 获取支付金额
     * */
    BigDecimal getAmount(Long orderNo);

    /*
     * 更新支付信息
     * */
    void update(PayTypeEnum payType, PayStatusEnum payStatus, String orderNo, String platformNo);

}
