package com.enqbs.app.service.pay;

import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.service.order.OrderService;
import com.enqbs.app.service.mq.RabbitMQService;
import com.enqbs.app.service.user.UserService;
import com.enqbs.common.constant.Constants;
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
        OrderVO orderVO = orderService.getOrderVO(orderNo);

        if (ObjectUtils.isEmpty(orderVO)) {
            throw new ServiceException("订单号:" + orderNo + ",订单不存在");
        }

        UserInfoVO userInfoVO = userService.getUserInfoVO();
        PayInfo payInfo = payInfoMapper.selectByOrderNoOrStatusOrDeleteStatus(orderNo, PayStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(payInfo) && !PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())
                && !OrderStatusEnum.NOT_PAY.getCode().equals(orderVO.getStatus())) {
            throw new ServiceException("订单号:" + orderNo + ",订单已关闭支付");
        }

        if (ObjectUtils.isEmpty(payInfo)) {
            insertPayInfo(orderNo, orderVO.getActualAmount(), userInfoVO);
        }

        return orderVO.getActualAmount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePayInfo(PayTypeEnum payTypeEnum, PayStatusEnum payStatusEnum, String orderNo, String platformNo) {
        PayInfo payInfo = payInfoMapper.selectByOrderNoOrStatusOrDeleteStatus(Long.valueOf(orderNo), null, Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(payInfo)) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付信息不存在");
        }

        if (!PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",订单已关闭支付");
        }

        payInfo.setStatus(payStatusEnum.getCode());
        updatePayInfo(orderNo, platformNo, payInfo);
        insertPayPlatform(payInfo.getId(), payTypeEnum, orderNo, platformNo);
        rabbitMQService.send(QueueEnum.PAY_SUCCESS_QUEUE.getExchange(), QueueEnum.PAY_SUCCESS_QUEUE.getRoutingKey(), payInfo);
    }

    private void insertPayInfo(Long orderNo, BigDecimal amount, UserInfoVO userInfo) {
        PayInfo payInfo = new PayInfo();
        payInfo.setOrderNo(orderNo);
        payInfo.setUserId(userInfo.getUserId());
        payInfo.setNickName(userInfo.getNickName());
        payInfo.setPhoto(userInfo.getPhoto());
        payInfo.setPayAmount(amount);
        payInfo.setStatus(PayStatusEnum.NOT_PAY.getCode());
        int row = payInfoMapper.insertSelective(payInfo);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",支付信息保存失败");
        }

        log.info("订单号:'{}',支付信息保存成功.", orderNo);
    }

    private void updatePayInfo(String orderNo, String platformNo, PayInfo payInfo) {
        int row = payInfoMapper.updateByPrimaryKeySelective(payInfo);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付信息更新失败");
        }

        log.info("订单号:'{}',支付平台流水号:'{}',支付信息更新成功.", orderNo, platformNo);
    }

    private void insertPayPlatform(Long payInfoId, PayTypeEnum payTypeEnum, String orderNo, String platformNo) {
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
