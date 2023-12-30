package com.enqbs.pay.service.Impl;

import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.AlipayTradeCloseDefaultResponse;
import com.alipay.v3.model.AlipayTradeCloseModel;
import com.alipay.v3.model.AlipayTradeQueryDefaultResponse;
import com.alipay.v3.model.AlipayTradeQueryModel;
import com.alipay.v3.util.GenericExecuteApi;
import com.enqbs.common.exception.ServiceException;
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
    private GenericExecuteApi payApi;

    @Resource
    private AlipayTradeApi tradeApi;

    @Override
    public String createPay(PayTypeEnum payType, Long orderNo, BigDecimal amount) {
        Map<String, Object> bizParams = new HashMap<>();
        Map<String, Object> bizContent = new HashMap<>();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", "商品消费");
        bizContent.put("product_code", payType.getPayType());
        bizParams.put("notify_url", aliPayConfig.getNotifyUrl());
        bizParams.put("return_url", aliPayConfig.getReturnUrl());
        bizParams.put("biz_content", bizContent);
        return switch (payType) {
            case ALIPAY_PC -> alipayPagePay(bizParams);     // 电脑网站支付
            case ALIPAY_WAP -> alipayWapPay(bizParams);     // 手机网站支付
            case ALIPAY_APP -> alipayAppPay(bizParams);     // APP 支付
            default -> throw new ServiceException("暂不支持的支付类型");
        };
    }

    @Override
    public void closePay(String orderNo, String payPlatformNo) {
        alipayClose(orderNo, payPlatformNo);
    }

    @Override
    public boolean asyncNotify(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String outTradeNo = request.getParameter("out_trade_no");
        String tradeNo = request.getParameter("trade_no");

        if (StringUtils.isNotEmpty(outTradeNo) && StringUtils.isNotEmpty(tradeNo)) {
            alipayQuery(outTradeNo, tradeNo);
            result = true;
        }

        return result;
    }

    private String alipayPagePay(Map<String, Object> bizParams) {
        try {
            return payApi.pageExecute("alipay.trade.page.pay", "POST", bizParams);
        } catch (ApiException e) {
            throw new ServiceException("调用电脑网站支付失败");
        }
    }

    private String alipayWapPay(Map<String, Object> bizParams) {
        try {
            return payApi.pageExecute("alipay.trade.wap.pay", "POST", bizParams);
        } catch (ApiException e) {
            throw new ServiceException("调用手机网站支付失败");
        }
    }

    private String alipayAppPay(Map<String, Object> bizParams) {
        try {
            return payApi.sdkExecute("alipay.trade.app.pay", bizParams);
        } catch (ApiException e) {
            throw new ServiceException("调用APP支付失败");
        }
    }

    private void alipayQuery(String orderNo, String payPlatformNo) {
        AlipayTradeQueryModel data = new AlipayTradeQueryModel();
        data.setOutTradeNo(orderNo);
        data.setTradeNo(payPlatformNo);

        try {
            tradeApi.query(data);
        } catch (ApiException e) {
            AlipayTradeQueryDefaultResponse errorObject = (AlipayTradeQueryDefaultResponse) e.getErrorObject();
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + payPlatformNo + ",查询失败,ERROR:" + errorObject);
        }
    }

    private void alipayClose(String orderNo, String payPlatformNo) {
        AlipayTradeCloseModel data = new AlipayTradeCloseModel();
        data.setOutTradeNo(orderNo);
        data.setTradeNo(payPlatformNo);

        try {
            tradeApi.close(data);
        } catch (ApiException e) {
            AlipayTradeCloseDefaultResponse errorObject = (AlipayTradeCloseDefaultResponse) e.getErrorObject();
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + payPlatformNo + ",关闭支付失败,ERROR:" + errorObject);
        }
    }

}
