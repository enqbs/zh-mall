package com.enqbs.pay.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
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
    public String pay(PayTypeEnum payTypeEnum, Long orderNo, BigDecimal amount) throws AlipayApiException {
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", "商品消费");
        bizContent.put("product_code", payTypeEnum.getPayType());
        String body;

        if (PayTypeEnum.ALIPAY_PAGE.getPayType().equals(payTypeEnum.getPayType())) {
            /* 电脑网站支付 */
            AlipayTradePagePayResponse response = alipayPagePay(bizContent);
            if (response.isSuccess()) {
                body = response.getBody();
            } else {
                throw new ServiceException("调用电脑网站支付失败");
            }
        } else if (PayTypeEnum.ALIPAY_WAP.getPayType().equals(payTypeEnum.getPayType())) {
            /* 手机网站支付 */
            AlipayTradeWapPayResponse response = alipayWapPay(bizContent);

            if (response.isSuccess()) {
                body = response.getBody();
            } else {
                throw new ServiceException("调用手机网站支付失败");
            }
        } else if (PayTypeEnum.ALIPAY_APP.getPayType().equals(payTypeEnum.getPayType())) {
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
    public boolean asyncNotify(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String outTradeNo = request.getParameter("out_trade_no");
        String tradeNo = request.getParameter("trade_no");

        if (StringUtils.isNotEmpty(outTradeNo) && StringUtils.isNotEmpty(tradeNo)) {
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", outTradeNo);
            bizContent.put("trade_no", tradeNo);

            try {
                AlipayTradeQueryResponse tradeQueryResponse = alipayQuery(bizContent);
                result = tradeQueryResponse.isSuccess();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private AlipayTradePagePayResponse alipayPagePay(JSONObject bizContent) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent.toString());
        return aliPayConfig.initAlipayClient().pageExecute(request);
    }

    private AlipayTradeWapPayResponse alipayWapPay(JSONObject bizContent) throws AlipayApiException {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent.toString());
        return aliPayConfig.initAlipayClient().pageExecute(request);
    }

    private AlipayTradeAppPayResponse alipayAppPay(JSONObject bizContent) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setBizContent(bizContent.toString());
        return aliPayConfig.initAlipayClient().sdkExecute(request);
    }

    private AlipayTradeQueryResponse alipayQuery(JSONObject bizContent) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(bizContent.toString());
        return aliPayConfig.initAlipayClient().execute(request);
    }

}