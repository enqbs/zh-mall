package com.enqbs.pay.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
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
import com.enqbs.common.util.GsonUtil;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("AliPay")
public class AliPayServiceImpl implements PayService {

    @Resource
    private AliPayConfig aliPayConfig;

    @Resource
    private AlipayClient aliPayClient;

    @Override
    public String createPay(PayTypeEnum payType, Long orderNo, BigDecimal amount) {
        Map<String, Object> bizContent = new HashMap<>();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", "商品消费");
        bizContent.put("product_code", payType.getPayType());
        String jsonString = GsonUtil.obj2Json(bizContent);
        String body;

        if (PayTypeEnum.ALIPAY_PC_PAGE.getPayType().equals(payType.getPayType())) {
            body = alipayPagePay(jsonString);       // 电脑网站支付
        } else if (PayTypeEnum.ALIPAY_WAP.getPayType().equals(payType.getPayType())) {
            body = alipayWapPay(jsonString);        // 手机网站支付
        } else if (PayTypeEnum.ALIPAY_APP.getPayType().equals(payType.getPayType())) {
            body = alipayAppPay(jsonString);        // APP 支付
        } else {
            throw new ServiceException("暂不支持的支付类型");
        }

        return body;
    }

    @Override
    public void closePay(String orderNo, String payPlatformNo) {
        Map<String, Object> bizContent = new HashMap<>();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("trade_no", payPlatformNo);
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(GsonUtil.obj2Json(bizContent));

        try {
            AlipayTradeCloseResponse response = aliPayClient.execute(request);

            if (response.isSuccess()) {
                log.info("订单号:'{}',支付平台流水号:'{}',关闭支付成功.", response.getOutTradeNo(), response.getTradeNo());
            } else {
                throw new ServiceException("订单号:" + response.getOutTradeNo() + ",支付平台流水号:" + response.getTradeNo() + ",关闭支付失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean asyncNotify(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String outTradeNo = request.getParameter("out_trade_no");
        String tradeNo = request.getParameter("trade_no");

        if (StringUtils.isNotEmpty(outTradeNo) && StringUtils.isNotEmpty(tradeNo)) {
            Map<String, Object> bizContent = new HashMap<>();
            bizContent.put("out_trade_no", outTradeNo);
            bizContent.put("trade_no", tradeNo);
            AlipayTradeQueryRequest tradeQueryRequest = new AlipayTradeQueryRequest();
            tradeQueryRequest.setBizContent(GsonUtil.obj2Json(bizContent));

            try {
                AlipayTradeQueryResponse tradeQueryResponse = aliPayClient.execute(tradeQueryRequest);
                result = tradeQueryResponse.isSuccess();
            } catch (AlipayApiException e) {
                throw new RuntimeException(e);
            }

        }

        return result;
    }

    private String alipayPagePay(String bizContent) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent);

        try {
            AlipayTradePagePayResponse response = aliPayClient.pageExecute(request);

            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new ServiceException("调用电脑网站支付失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String alipayWapPay(String bizContent) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent);

        try {
            AlipayTradeWapPayResponse response = aliPayClient.pageExecute(request);

            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new ServiceException("调用手机网站支付失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String alipayAppPay(String bizContent) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setBizContent(bizContent);

        try {
            AlipayTradeAppPayResponse response = aliPayClient.sdkExecute(request);

            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new ServiceException("调用APP支付失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

}
