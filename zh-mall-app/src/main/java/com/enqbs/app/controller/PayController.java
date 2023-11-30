package com.enqbs.app.controller;

import com.enqbs.app.service.pay.PayInfoService;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.pay.enums.PayStatusEnum;
import com.enqbs.pay.enums.PayTypeEnum;
import com.enqbs.pay.factory.PayFactory;
import com.enqbs.pay.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Resource
    private PayFactory payFactory;

    @Resource
    private PayInfoService payInfoService;

    @GetMapping("/alipay-pc/{orderNo}")
    public ModelAndView payAliPayPC(@PathVariable Long orderNo) {
        PayService payService = payFactory.getPayService(PayTypeEnum.ALIPAY_PC_PAGE);
        String pay = payService.pay(PayTypeEnum.ALIPAY_PC_PAGE, orderNo, payInfoService.getPayAmount(orderNo));
        Map<String, Object> map = new HashMap<>();
        map.put("body", pay);
        return new ModelAndView("alipay_page_pay", map);
    }

    @PostMapping("/async-notify")
    public void asyncNotify(HttpServletRequest request, HttpServletResponse response) {
        String orderNo = request.getParameter("out_trade_no");
        String platformNo = request.getParameter("trade_no");
        PayService payService = payFactory.getPayService(PayTypeEnum.ALIPAY_PC_PAGE);
        boolean result = payService.asyncNotify(request, response);

        if (result) {
            payInfoService.update(PayTypeEnum.ALIPAY_PC_PAGE, PayStatusEnum.PAY_SUCCESS, orderNo, platformNo);
            payService.closePay(orderNo, platformNo);       // 关闭支付
        } else {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付回调通知异常");
        }
    }

}
