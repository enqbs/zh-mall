package com.enqbs.pay.factory;

import com.enqbs.common.exception.ServiceException;
import com.enqbs.pay.enums.PayTypeEnum;
import com.enqbs.pay.service.PayService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * 利用工厂模式决定支付平台、支付实现方式
 * */
@Component
public class PayFactory {

    @Resource
    private final Map<String, PayService> payServiceMap = new ConcurrentHashMap<>();

    public PayService getPayService(PayTypeEnum payTypeEnum) {
        PayService payService = payServiceMap.get(payTypeEnum.getPayPlatform());

        if (ObjectUtils.isEmpty(payService)) {
            throw new ServiceException("暂不支持的支付平台");
        }

        return payService;
    }

}
