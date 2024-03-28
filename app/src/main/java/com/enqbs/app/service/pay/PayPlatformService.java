package com.enqbs.app.service.pay;

import com.enqbs.pay.enums.PayTypeEnum;

public interface PayPlatformService {

    /**
     * 保存支付平台信息
     *
     * @param payInfoId 支付信息 ID
     * @param payTypeEnum 支付方式（平台）
     * @param orderNo 订单号
     * @param platformNo 支付平台流水号
     * @return row
     */
    int insert(Long payInfoId, PayTypeEnum payTypeEnum, String orderNo, String platformNo);

}
