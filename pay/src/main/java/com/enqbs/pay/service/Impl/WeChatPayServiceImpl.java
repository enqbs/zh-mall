package com.enqbs.pay.service.Impl;

import com.enqbs.pay.enums.PayTypeEnum;
import com.enqbs.pay.service.PayService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Service("WeChatPay")
public class WeChatPayServiceImpl implements PayService {

    @Override
    public String createPay(PayTypeEnum payType, Long orderNo, BigDecimal amount) {
        System.out.println("WeChatPay");
        return "WeChatPay";
    }

    @Override
    public void closePay(String orderNo, String payPlatformNo) {
        System.out.println("WeChatPay关闭交易");
    }

    @Override
    public boolean asyncNotify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("WeChatPay异步通知");
        return false;
    }

}
