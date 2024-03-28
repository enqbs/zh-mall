package com.enqbs.pay.service;

import com.enqbs.pay.enums.PayTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

public interface PayService {

    /**
     * 发起支付
     *
     * @param payType 支付方式（平台）
     * @param orderNo 订单号
     * @param amount 支付金额
     * @return 支付请求
     */
    String createPay(PayTypeEnum payType, Long orderNo, BigDecimal amount);

    /**
     * 关闭支付
     *
     * @param orderNo 订单号
     * @param payPlatformNo 支付平台流水号
     */
    void closePay(String orderNo, String payPlatformNo);

    /**
     * 支付成功异步通知
     *
     * @param request request
     * @param response response
     * @return 是否支付成功
     */
    boolean asyncNotify(HttpServletRequest request, HttpServletResponse response);

}
