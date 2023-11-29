package com.enqbs.pay.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.pay.config.AliPayConfig;
import com.enqbs.pay.enums.PayTypeEnum;
import com.enqbs.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Slf4j
@Service("AliPay")
public class AliPayServiceImpl implements PayService {

    @Resource
    private AliPayConfig aliPayConfig;

    @Override
    public String pay(PayTypeEnum payType, Long orderNo, BigDecimal amount) {
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", "商品消费");
        bizContent.put("product_code", payType.getPayType());
        String body;

        if (PayTypeEnum.ALIPAY_PC_PAGE.getPayType().equals(payType.getPayType())) {
            /* 电脑网站支付 */
            AlipayTradePagePayResponse response = alipayPagePay(bizContent);

            if (response.isSuccess()) {
                body = response.getBody();
            } else {
                throw new ServiceException("调用电脑网站支付失败");
            }
        } else if (PayTypeEnum.ALIPAY_WAP.getPayType().equals(payType.getPayType())) {
            /* 手机网站支付 */
            AlipayTradeWapPayResponse response = alipayWapPay(bizContent);

            if (response.isSuccess()) {
                body = response.getBody();
            } else {
                throw new ServiceException("调用手机网站支付失败");
            }
        } else if (PayTypeEnum.ALIPAY_APP.getPayType().equals(payType.getPayType())) {
            /* APP 支付 */
            AlipayTradeAppPayResponse response = alipayAppPay(bizContent);

            if (response.isSuccess()) {
                body = response.getBody();
            } else {
                throw new ServiceException("调用APP支付失败");
            }
        } else {
            throw new ServiceException("暂不支持的支付类型");
        }

        return body;
    }

    @Override
    public void closePay(String orderNo, String payPlatformNo) {
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("trade_no", payPlatformNo);
        AlipayTradeCloseResponse response = alipayClosePay(bizContent);

        if (response.isSuccess()) {
            log.info("订单号:'{}',支付平台流水号:'{}',关闭支付成功.", orderNo, payPlatformNo);
        } else {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + payPlatformNo + ",关闭支付失败");
        }
    }

    @Override
    public boolean asyncNotify(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String outTradeNo = request.getParameter("out_trade_no");
        String tradeNo = request.getParameter("trade_no");

        if (StringUtils.isNotEmpty(outTradeNo) && StringUtils.isNotEmpty(tradeNo)) {
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", outTradeNo);
            bizContent.put("trade_no", tradeNo);
            AlipayTradeQueryResponse tradeQueryResponse = alipayQuery(bizContent);
            result = tradeQueryResponse.isSuccess();
        }

        return result;
    }

    private AlipayTradePagePayResponse alipayPagePay(JSONObject bizContent) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent.toString());

        try {
            return aliPayConfig.initAlipayClient().pageExecute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private AlipayTradeWapPayResponse alipayWapPay(JSONObject bizContent) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent.toString());

        try {
            return aliPayConfig.initAlipayClient().pageExecute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private AlipayTradeAppPayResponse alipayAppPay(JSONObject bizContent) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setBizContent(bizContent.toString());

        try {
            return aliPayConfig.initAlipayClient().sdkExecute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private AlipayTradeQueryResponse alipayQuery(JSONObject bizContent) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(bizContent.toString());

        try {
            return aliPayConfig.initAlipayClient().execute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private AlipayTradeCloseResponse alipayClosePay(JSONObject bizContent) {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(bizContent.toString());

        try {
            return aliPayConfig.initAlipayClient().execute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

}
