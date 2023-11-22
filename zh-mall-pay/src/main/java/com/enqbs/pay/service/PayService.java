package com.enqbs.pay.service;

import com.enqbs.pay.enums.PayTypeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public interface PayService {

    String pay(PayTypeEnum payTypeEnum, Long orderNo, BigDecimal amount);

    void closePay(String orderNo, String payPlatformNo);

    boolean asyncNotify(HttpServletRequest request, HttpServletResponse response);

}
