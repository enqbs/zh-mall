package com.enqbs.app.controller;

import com.enqbs.app.service.pay.PayInfoService;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.pay.enums.PayStatusEnum;
import com.enqbs.pay.enums.PayTypeEnum;
import com.enqbs.pay.factory.PayFactory;
import com.enqbs.pay.service.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Resource
    private PayFactory payFactory;

    @Resource
    private PayInfoService payInfoService;

    @GetMapping("/alipay-page/{orderNo}")
    public ModelAndView aliPayPage(@PathVariable Long orderNo) {
        PayService payService = payFactory.getPayService(PayTypeEnum.ALIPAY_PC);
        String body = payService.createPay(PayTypeEnum.ALIPAY_PC, orderNo, payInfoService.getAmount(orderNo));
        return new ModelAndView("alipay-page").addObject("body", body);
    }

    @PostMapping("/async-notify")
    public void asyncNotify(HttpServletRequest request, HttpServletResponse response) {
        String orderNo = request.getParameter("out_trade_no");
        String platformNo = request.getParameter("trade_no");
        PayService payService = payFactory.getPayService(PayTypeEnum.ALIPAY_PC);
        boolean result = payService.asyncNotify(request, response);

        if (result) {
            Thread.ofVirtual().name("asyncNotify-closePay").start(() -> payService.closePay(orderNo, platformNo));
            payInfoService.update(PayTypeEnum.ALIPAY_PC, PayStatusEnum.PAY_SUCCESS, orderNo, platformNo);
        } else {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付回调通知异常");
        }
    }

}
