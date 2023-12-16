package com.enqbs.app.service.pay;

import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.service.order.OrderService;
import com.enqbs.app.service.mq.RabbitMQService;
import com.enqbs.app.service.user.UserService;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.OrderStatusEnum;
import com.enqbs.app.enums.QueueEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.PayInfoMapper;
import com.enqbs.generator.pojo.PayInfo;
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
    private PayPlatformService payPlatformService;
    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;
    @Resource
    private RabbitMQService rabbitMQService;

    @Override
    public BigDecimal getPayAmount(Long orderNo) {
        OrderVO orderVO = orderService.getOrderVO(orderNo);
        PayInfo payInfo = payInfoMapper.selectByOrderNoOrStatusOrDeleteStatus(orderNo, PayStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(payInfo) && !PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())
                && !OrderStatusEnum.NOT_PAY.getCode().equals(orderVO.getStatus())) {
            throw new ServiceException("订单号:" + orderNo + ",订单已关闭支付");
        }

        if (ObjectUtils.isEmpty(payInfo)) {
            payInfo.setOrderNo(orderVO.getOrderNo());
            payInfo.setUserId(orderVO.getUserId());
            payInfo.setPayAmount(orderVO.getActualAmount());
            payInfo.setStatus(PayStatusEnum.NOT_PAY.getCode());
            int row = payInfoMapper.insertSelective(payInfo);

            if (row <= 0) {
                throw new ServiceException("订单号:" + orderNo + ",支付信息保存失败");
            }

            log.info("订单号:'{}',支付信息保存成功.", orderNo);
        }

        return orderVO.getActualAmount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PayTypeEnum payType, PayStatusEnum payStatus, String orderNo, String platformNo) {
        PayInfo payInfo = payInfoMapper.selectByOrderNoOrStatusOrDeleteStatus(Long.valueOf(orderNo), null, Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(payInfo)) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付信息不存在");
        }

        if (!PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",订单已关闭支付");
        }

        UserInfoVO userInfoVO = userService.getUserInfoVO();
        payInfo.setStatus(payStatus.getCode());
        payInfo.setNickName(userInfoVO.getNickName());
        payInfo.setPhoto(userInfoVO.getPhoto());
        int row = payInfoMapper.updateByPrimaryKeySelective(payInfo);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付信息更新失败");
        }

        row = payPlatformService.insert(payInfo.getId(), payType, orderNo, platformNo);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付平台信息保存失败");
        }

        rabbitMQService.send(QueueEnum.PAY_SUCCESS_QUEUE, orderNo);
        log.info("订单号:'{}',支付平台流水号:'{}',支付信息更新成功.", orderNo, platformNo);
    }

}
