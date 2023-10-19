package com.enqbs.app.service.impl;

import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.service.OrderService;
import com.enqbs.app.service.PayInfoService;
import com.enqbs.app.service.RabbitMQService;
import com.enqbs.app.service.UserService;
import com.enqbs.common.enums.OrderStatusEnum;
import com.enqbs.common.enums.QueueEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.PayInfoMapper;
import com.enqbs.generator.dao.PayPlatformMapper;
import com.enqbs.generator.dao.PayRefundMapper;
import com.enqbs.generator.pojo.PayInfo;
import com.enqbs.generator.pojo.PayPlatform;
import com.enqbs.pay.enums.PayStatusEnum;
import com.enqbs.pay.enums.PayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class PayInfoServiceImpl implements PayInfoService {

    @Resource
    private PayInfoMapper payInfoMapper;

    @Resource
    private PayPlatformMapper payPlatformMapper;

    @Resource
    private PayRefundMapper payRefundMapper;

    @Resource
    private UserService userService;

    @Resource
    private OrderService orderService;

    @Resource
    private RabbitMQService rabbitMQService;

    @Override
    public PayInfo insertPayInfo(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        OrderVO orderVO = orderService.getOrderVO(orderNo);

        if (!OrderStatusEnum.NOT_PAY.getCode().equals(orderVO.getStatus())) {
            throw new ServiceException("该订单已关闭支付");
        }
        PayInfo payInfo = payInfoMapper.selectByOrderNo(orderNo);

        if (ObjectUtils.isEmpty(payInfo)) {
            payInfo.setOrderNo(orderNo);
            payInfo.setUserId(userInfoVO.getUserId());
            payInfo.setNickName(userInfoVO.getNickName());
            payInfo.setPhoto(userInfoVO.getPhoto());
            payInfo.setPayAmount(orderVO.getAmount());
            int insertRow = payInfoMapper.insertSelective(payInfo);

            if (insertRow <= 0) {
                throw new ServiceException("支付信息保存失败");
            }
            log.info("支付信息保存成功");
        }

        if (!PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())) {
            throw new ServiceException("该订单已关闭支付");
        }
        return payInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePayInfo(PayTypeEnum payTypeEnum, String orderNo, String platformNo) {
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.valueOf(orderNo));

        if (ObjectUtils.isEmpty(payInfo)) {
            throw new ServiceException("支付信息不存在");
        }

        if (!PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())) {
            throw new ServiceException("该订单已关闭支付");
        }
        payInfo.setStatus(PayStatusEnum.PAY_SUCCESS.getCode());
        int updateRow = payInfoMapper.updateByPrimaryKeySelective(payInfo);

        if (updateRow <= 0) {
            throw new ServiceException("支付信息更新失败");
        }
        log.info("支付信息更新成功");
        insertPayPlatform(payTypeEnum, orderNo, platformNo, payInfo.getId());
        rabbitMQService.send(QueueEnum.PAY_SUCCESS_QUEUE.getExchange(), QueueEnum.PAY_SUCCESS_QUEUE.getRoutingKey(), payInfo);
    }

    private void insertPayPlatform(PayTypeEnum payTypeEnum, String orderNo, String platformNo, Long payInfoId) {
        PayPlatform payPlatform = new PayPlatform();
        payPlatform.setPayInfoId(payInfoId);
        payPlatform.setOrderNo(Long.valueOf(orderNo));
        payPlatform.setPayType(payTypeEnum.getPayType());
        payPlatform.setPlatform(payTypeEnum.getPayPlatform());
        payPlatform.setPlatformNumber(platformNo);
        int insertRow = payPlatformMapper.insertSelective(payPlatform);

        if (insertRow <= 0) {
            throw new ServiceException("支付平台信息保存失败");
        }
        log.info("支付平台信息保存成功");
    }

}
