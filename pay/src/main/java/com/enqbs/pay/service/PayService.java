package com.enqbs.pay.service;

import com.enqbs.pay.enums.PayTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

public interface PayService {

    String createPay(PayTypeEnum payType, Long orderNo, BigDecimal amount);

    void closePay(String orderNo, String payPlatformNo);

    boolean asyncNotify(HttpServletRequest request, HttpServletResponse response);

}
