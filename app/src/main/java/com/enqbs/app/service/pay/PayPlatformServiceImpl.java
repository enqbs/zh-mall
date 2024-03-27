package com.enqbs.app.service.pay;

import com.enqbs.generator.dao.PayPlatformMapper;
import com.enqbs.generator.pojo.PayPlatform;
import com.enqbs.pay.enums.PayTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PayPlatformServiceImpl implements PayPlatformService {

    @Resource
    private PayPlatformMapper payPlatformMapper;

    @Override
    public int insert(Long payInfoId, PayTypeEnum payTypeEnum, String orderNo, String platformNo) {
        PayPlatform payPlatform = new PayPlatform();
        payPlatform.setPayInfoId(payInfoId);
        payPlatform.setOrderNo(Long.valueOf(orderNo));
        payPlatform.setPayType(payTypeEnum.getPayType());
        payPlatform.setPlatform(payTypeEnum.getPayPlatform());
        payPlatform.setPlatformNo(platformNo);
        return payPlatformMapper.insertSelective(payPlatform);
    }

}
