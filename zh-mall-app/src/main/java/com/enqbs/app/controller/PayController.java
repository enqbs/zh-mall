package com.enqbs.app.controller;

import com.alipay.api.AlipayApiException;
import com.enqbs.app.service.PayInfoService;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.pojo.PayInfo;
import com.enqbs.pay.enums.PayTypeEnum;
import com.enqbs.pay.factory.PayFactory;
import com.enqbs.pay.service.PayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PayController {

    @Resource
    private PayFactory payFactory;

    @Resource
    private PayInfoService payInfoService;

    @GetMapping("/pay/{orderNo}")
    public ModelAndView alipayPagePay(@PathVariable Long orderNo) throws AlipayApiException {
        PayInfo payInfo = payInfoService.insertPayInfo(orderNo);
        PayService payService = payFactory.getPayService(PayTypeEnum.ALIPAY_PAGE);
        String pay = payService.pay(PayTypeEnum.ALIPAY_PAGE, orderNo, payInfo.getPayAmount());
        Map<String, Object> map = new HashMap<>();
        map.put("body", pay);
        return new ModelAndView("alipay_page_pay", map);
    }

    @PostMapping("/pay/async-notify")
    public void asyncNotify(HttpServletRequest request, HttpServletResponse response) {
        PayService payService = payFactory.getPayService(PayTypeEnum.ALIPAY_PAGE);
        boolean result = payService.asyncNotify(request, response);

        if (result) {
            String outTradeNo = request.getParameter("out_trade_no");
            String tradeNo = request.getParameter("trade_no");
            payInfoService.updatePayInfo(PayTypeEnum.ALIPAY_PAGE, outTradeNo, tradeNo);
        } else {
            throw new ServiceException("支付回调通知异常");
        }
    }

}
