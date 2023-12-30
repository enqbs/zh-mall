package com.enqbs.app.service.pay;

import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.service.order.OrderService;
import com.enqbs.app.service.mq.RabbitMQService;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.OrderStatusEnum;
import com.enqbs.app.enums.QueueEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.PayInfoMapper;
import com.enqbs.generator.pojo.PayInfo;
import com.enqbs.pay.enums.PayStatusEnum;
import com.enqbs.pay.enums.PayTypeEnum;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PayInfoServiceImpl implements PayInfoService {

    @Resource
    private PayInfoMapper payInfoMapper;
    @Resource
    private PayPlatformService payPlatformService;
    @Resource
    private OrderService orderService;
    @Resource
    private RabbitMQService rabbitMQService;

    @Override
    public BigDecimal getAmount(Long orderNo) {
        OrderVO orderVO = orderService.getOrderVO(orderNo);
        PayInfo payInfo = payInfoMapper.selectByOrderNoOrStatusOrDeleteStatus(orderNo, PayStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(payInfo)
                && !PayStatusEnum.NOT_PAY.getCode().equals(payInfo.getStatus())
                && !OrderStatusEnum.NOT_PAY.getCode().equals(orderVO.getStatus())) {
            throw new ServiceException("订单号:" + orderNo + ",订单已关闭支付");
        }

        if (ObjectUtils.isEmpty(payInfo)) {
            payInfo = new PayInfo();
            payInfo.setOrderNo(orderNo);
            payInfo.setUserId(orderVO.getUserId());
            payInfo.setPayAmount(orderVO.getActualAmount());
            payInfo.setStatus(PayStatusEnum.NOT_PAY.getCode());
            payInfoMapper.insertSelective(payInfo);
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

        payInfo.setStatus(payStatus.getCode());
        int row = payInfoMapper.updateByPrimaryKeySelective(payInfo);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付信息更新失败");
        }

        row = payPlatformService.insert(payInfo.getId(), payType, orderNo, platformNo);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",支付平台流水号:" + platformNo + ",支付平台信息保存失败");
        }

        rabbitMQService.send(QueueEnum.PAY_SUCCESS_QUEUE, orderNo);
    }

}
