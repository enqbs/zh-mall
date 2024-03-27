package com.enqbs.pay.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.pay.config.AliPayConfig;
import com.enqbs.pay.enums.PayTypeEnum;
import com.enqbs.pay.service.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
        bizContent.put("subject", "商品订单-" + orderNo);
        bizContent.put("product_code", payType.getPayType());
        String jsonStr = GsonUtil.obj2Json(bizContent);

        return switch (payType) {
            case ALIPAY_PC -> alipayPagePay(jsonStr);     // 电脑网站支付
            case ALIPAY_WAP -> alipayWapPay(jsonStr);     // 手机网站支付
            case ALIPAY_APP -> alipayAppPay(jsonStr);     // APP 支付
            default -> throw new ServiceException("暂不支持的支付类型");
        };
    }

    @Override
    public void closePay(String orderNo, String payPlatformNo) {
        Map<String, Object> bizContent = new HashMap<>();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("trade_no", payPlatformNo);
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(GsonUtil.obj2Json(bizContent));

        try {
            aliPayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + payPlatformNo + ",关闭支付失败");
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
            AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
            queryRequest.setBizContent(GsonUtil.obj2Json(bizContent));

            try {
                AlipayTradeQueryResponse queryResponse = aliPayClient.execute(queryRequest);
                result = queryResponse.isSuccess();
            } catch (AlipayApiException e) {
                throw new ServiceException("订单号:" + outTradeNo + ",支付平台流水号:" + tradeNo + ",查询失败");
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
            AlipayTradePagePayResponse response = aliPayClient.pageExecute(request,"POST");
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new ServiceException("调用电脑网站支付失败");
        }
    }

    private String alipayWapPay(String bizContent) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        request.setBizContent(bizContent);

        try {
            AlipayTradeWapPayResponse response = aliPayClient.pageExecute(request,"POST");
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new ServiceException("调用手机网站支付失败");
        }
    }

    private String alipayAppPay(String bizContent) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setBizContent(bizContent);

        try {
            AlipayTradeAppPayResponse response = aliPayClient.sdkExecute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new ServiceException("调用APP支付失败");
        }
    }

}
