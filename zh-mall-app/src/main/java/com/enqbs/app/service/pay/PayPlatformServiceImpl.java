package com.enqbs.app.service.pay;

import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.PayPlatformMapper;
import com.enqbs.generator.pojo.PayPlatform;
import com.enqbs.pay.enums.PayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class PayPlatformServiceImpl implements PayPlatformService {

    @Resource
    private PayPlatformMapper payPlatformMapper;

    @Override
    public void insert(Long payInfoId, PayTypeEnum payTypeEnum, String orderNo, String platformNo) {
        PayPlatform payPlatform = new PayPlatform();
        payPlatform.setPayInfoId(payInfoId);
        payPlatform.setOrderNo(Long.valueOf(orderNo));
        payPlatform.setPayType(payTypeEnum.getPayType());
        payPlatform.setPlatform(payTypeEnum.getPayPlatform());
        payPlatform.setPlatformNo(platformNo);
        int row = payPlatformMapper.insertSelective(payPlatform);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付平台信息保存失败");
        }

        log.info("订单号:'{}',支付平台流水号:'{}',支付平台信息保存成功.", orderNo, platformNo);
    }

}
