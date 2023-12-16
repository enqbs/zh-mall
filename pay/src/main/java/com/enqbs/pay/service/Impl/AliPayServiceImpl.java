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
    public String createPay(PayTypeEnum payType, Long orderNo, BigDecimal amount) {
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", "商品消费");
        bizContent.put("product_code", payType.getPayType());
        String body;

        if (PayTypeEnum.ALIPAY_PC_PAGE.getPayType().equals(payType.getPayType())) {
            body = alipayPagePay(bizContent);       // 电脑网站支付
        } else if (PayTypeEnum.ALIPAY_WAP.getPayType().equals(payType.getPayType())) {
            body = alipayWapPay(bizContent);        // 手机网站支付
        } else if (PayTypeEnum.ALIPAY_APP.getPayType().equals(payType.getPayType())) {
            body = alipayAppPay(bizContent);        // APP 支付
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
        alipayClose(bizContent);
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
            result = alipayQuery(bizContent);
        }

        return result;
    }

    private String alipayPagePay(JSONObject bizContent) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent.toString());

        try {
            AlipayTradePagePayResponse response = aliPayConfig.aliPayClient().pageExecute(request);

            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new ServiceException("调用电脑网站支付失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String alipayWapPay(JSONObject bizContent) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent.toString());

        try {
            AlipayTradeWapPayResponse response = aliPayConfig.aliPayClient().pageExecute(request);

            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new ServiceException("调用手机网站支付失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String alipayAppPay(JSONObject bizContent) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setBizContent(bizContent.toString());

        try {
            AlipayTradeAppPayResponse response = aliPayConfig.aliPayClient().sdkExecute(request);

            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new ServiceException("调用APP支付失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean alipayQuery(JSONObject bizContent) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(bizContent.toString());

        try {
            AlipayTradeQueryResponse response = aliPayConfig.aliPayClient().execute(request);
            return response.isSuccess();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void alipayClose(JSONObject bizContent) {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(bizContent.toString());

        try {
            AlipayTradeCloseResponse response = aliPayConfig.aliPayClient().execute(request);

            if (response.isSuccess()) {
                log.info("订单号:'{}',支付平台流水号:'{}',关闭支付成功.", response.getOutTradeNo(), response.getTradeNo());
            } else {
                throw new ServiceException("订单号:" + response.getOutTradeNo() +
                        ",支付平台流水号:" + response.getTradeNo() + ",关闭支付失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

}
