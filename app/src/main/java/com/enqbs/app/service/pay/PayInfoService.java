package com.enqbs.app.service.pay;

import com.enqbs.pay.enums.PayStatusEnum;
import com.enqbs.pay.enums.PayTypeEnum;

import java.math.BigDecimal;

public interface PayInfoService {

    /**
     * 获取支付金额
     *
     * @param orderNo 订单号
     * @return 支付金额
     */
    BigDecimal getAmount(Long orderNo);

    /**
     * 更新支付信息
     *
     * @param payType 支付方式（平台）
     * @param payStatus 支付状态
     * @param orderNo 订单号
     * @param platformNo 支付平台流水号
     */
    void update(PayTypeEnum payType, PayStatusEnum payStatus, String orderNo, String platformNo);

}
