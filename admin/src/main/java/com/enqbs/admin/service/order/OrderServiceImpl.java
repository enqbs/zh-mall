package com.enqbs.admin.service.order;

import com.enqbs.admin.convert.OrderConvert;
import com.enqbs.admin.enums.OrderStatusEnum;
import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.vo.OrderItemVO;
import com.enqbs.admin.vo.OrderLogisticsInfoVO;
import com.enqbs.admin.vo.OrderShippingAddressVO;
import com.enqbs.admin.vo.OrderVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.OrderMapper;
import com.enqbs.generator.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private OrderShippingAddressService orderShippingAddressService;
    @Resource
    private OrderLogisticsInfoService orderLogisticsInfoService;
    @Resource
    private OrderConvert orderConvert;

    @Override
    public PageUtil<OrderVO> getOrderVOList(Long orderNo, String orderSc, Integer userId,
                                            Integer paymentType, Integer status, Integer deleteStatus,
                                            SortEnum sort, Integer pageNum, Integer pageSize) {
        PageUtil<OrderVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<Order> orderList = orderMapper.selectListByParam(orderNo, orderSc, userId, paymentType,
                status, deleteStatus, sort.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(orderList)) {
            return pageUtil;
        }

        Long total = orderMapper.countByParam(orderNo, orderSc, userId, paymentType, status, deleteStatus);
        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        /* List to Map */
        Map<Long, List<OrderItemVO>> orderItemVOListMap = orderItemService
                .getOrderItemVOList(orderNoSet).stream().collect(Collectors.groupingBy(OrderItemVO::getOrderNo));
        Map<Long, OrderShippingAddressVO> orderShippingAddressVOMap = orderShippingAddressService
                .getOrderShippingAddressVOList(orderNoSet).stream().collect(Collectors.toMap(OrderShippingAddressVO::getOrderNo, v -> v));
        Map<Long, OrderLogisticsInfoVO> orderLogisticsInfoVOMap = orderLogisticsInfoService
                .getOrderLogisticsInfoVOList(orderNoSet).stream().collect(Collectors.toMap(OrderLogisticsInfoVO::getOrderNo, v -> v));

        List<OrderVO> orderVOList = orderList.stream().map(e -> {
                    OrderVO orderVO = orderConvert.order2OrderVO(e);
                    orderVO.setShippingAddress(orderShippingAddressVOMap.get(orderVO.getOrderNo()));
                    orderVO.setLogisticsInfo(orderLogisticsInfoVOMap.get(orderVO.getOrderNo()));
                    orderVO.setOrderItemList(orderItemVOListMap.get(orderVO.getOrderNo()));
                    return orderVO;
                }
        ).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(orderVOList);
        return pageUtil;
    }

    @Override
    public OrderVO getOrderVO(Long orderNo) {
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, null, null, Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            return new OrderVO();
        }

        List<OrderItemVO> orderItemVOList = orderItemService.getOrderItemVOList(orderNo);
        OrderShippingAddressVO orderShippingAddressVO = orderShippingAddressService.getOrderShippingAddressVO(orderNo);
        OrderLogisticsInfoVO orderLogisticsInfoVO = orderLogisticsInfoService.getOrderLogisticsInfoVO(orderNo);

        OrderVO orderVO = orderConvert.order2OrderVO(order);
        orderVO.setOrderItemList(orderItemVOList);
        orderVO.setShippingAddress(orderShippingAddressVO);
        orderVO.setLogisticsInfo(orderLogisticsInfoVO);
        return orderVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shipment(Long orderNo, LogisticsInfoForm form) {
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, null,
                OrderStatusEnum.PAY_SUCCESS.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单号:" + orderNo + ",订单不满足发货条件");
        }

        int row = orderLogisticsInfoService.insert(orderNo, form);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单快递信息保存失败");
        }

        order.setStatus(OrderStatusEnum.NOT_RECEIPT.getCode());
        row = orderMapper.updateByPrimaryKeySelective(order);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单状态修改失败");
        }

        log.info("订单号:'{}',发货成功.", orderNo);
    }

}
