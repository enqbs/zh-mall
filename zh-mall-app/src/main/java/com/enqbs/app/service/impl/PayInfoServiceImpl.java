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
import java.math.BigDecimal;

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
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal getPayAmount(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        OrderVO orderVO = orderService.getOrderVO(orderNo);
        PayInfo payInfo = payInfoMapper.selectByOrderNo(orderNo);

        if (ObjectUtils.isEmpty(orderVO) || userInfoVO.getUserId().equals(orderVO.getUserId())) {
            throw new ServiceException("订单不存在,订单号:" + orderNo);
        }

        if (ObjectUtils.isNotEmpty(orderVO) && !OrderStatusEnum.NOT_PAY.getCode().equals(orderVO.getStatus())
                && ObjectUtils.isNotEmpty(payInfo) && !PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())) {
            throw new ServiceException("订单已关闭支付,订单号:" + orderNo);
        }

        if (ObjectUtils.isEmpty(payInfo)) {
            insertPayInfo(orderNo, orderVO.getAmount(), userInfoVO);
        }

        return orderVO.getAmount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePayInfo(PayTypeEnum payTypeEnum, PayStatusEnum payStatusEnum, String orderNo, String platformNo) {
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.valueOf(orderNo));

        if (ObjectUtils.isEmpty(payInfo)) {
            throw new ServiceException("支付信息不存在,订单号:" + orderNo + ",支付平台流水号:" + platformNo);
        }

        if (!PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())) {
            throw new ServiceException("订单已关闭支付,订单号:" + orderNo + ",支付平台流水号:" + platformNo);
        }

        payInfo.setStatus(payStatusEnum.getCode());
        updatePayInfo(orderNo, platformNo, payInfo);
        insertPayPlatform(payTypeEnum, orderNo, platformNo, payInfo.getId());
        rabbitMQService.send(QueueEnum.PAY_SUCCESS_QUEUE.getExchange(), QueueEnum.PAY_SUCCESS_QUEUE.getRoutingKey(), payInfo);
        log.info("支付平台信息保存成功,订单号:'{}',支付平台流水号:'{}'.", orderNo, platformNo);
    }

    private void insertPayInfo(Long orderNo, BigDecimal amount, UserInfoVO userInfo) {
        PayInfo payInfo = new PayInfo();
        payInfo.setOrderNo(orderNo);
        payInfo.setUserId(userInfo.getUserId());
        payInfo.setNickName(userInfo.getNickName());
        payInfo.setPhoto(userInfo.getPhoto());
        payInfo.setPayAmount(amount);
        int row = payInfoMapper.insertSelective(payInfo);

        if (row <= 0) {
            throw new ServiceException("支付信息保存失败,订单号:" + orderNo);
        }

        log.info("支付信息保存成功,订单号:'{}'.", orderNo);
    }

    private void updatePayInfo(String orderNo, String platformNo, PayInfo payInfo) {
        int row = payInfoMapper.updateByPrimaryKeySelective(payInfo);

        if (row <= 0) {
            throw new ServiceException("支付信息更新失败,订单号:" + orderNo + ",支付平台流水号:" + platformNo);
        }

        log.info("支付信息更新成功,订单号:'{}',支付平台流水号:'{}'.", orderNo, platformNo);
    }

    private void insertPayPlatform(PayTypeEnum payTypeEnum, String orderNo, String platformNo, Long payInfoId) {
        PayPlatform payPlatform = new PayPlatform();
        payPlatform.setPayInfoId(payInfoId);
        payPlatform.setOrderNo(Long.valueOf(orderNo));
        payPlatform.setPayType(payTypeEnum.getPayType());
        payPlatform.setPlatform(payTypeEnum.getPayPlatform());
        payPlatform.setPlatformNumber(platformNo);
        int row = payPlatformMapper.insertSelective(payPlatform);

        if (row <= 0) {
            throw new ServiceException("支付平台信息保存失败,订单号:" + orderNo + ",支付平台流水号:" + platformNo);
        }

        log.info("支付平台信息保存成功,订单号:'{}',支付平台流水号:'{}'.", orderNo, platformNo);
    }

}
