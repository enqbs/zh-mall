package com.enqbs.admin.service.order;

import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.vo.OrderItemVO;
import com.enqbs.admin.vo.OrderLogisticsInfoVO;
import com.enqbs.admin.vo.OrderShippingAddressVO;
import com.enqbs.admin.vo.OrderVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.OrderStatusEnum;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.OrderItemMapper;
import com.enqbs.generator.dao.OrderLogisticsInfoMapper;
import com.enqbs.generator.dao.OrderMapper;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.OrderItem;
import com.enqbs.generator.pojo.OrderLogisticsInfo;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private OrderShippingAddressMapper orderShippingAddressMapper;
    @Resource
    private OrderLogisticsInfoMapper orderLogisticsInfoMapper;

    @Override
    public PageUtil<OrderVO> getOrderVOList(Long orderNo, String orderSc, Integer userId,
                                            Integer paymentType, Integer status, Integer deleteStatus,
                                            SortEnum sortEnum, Integer pageNum, Integer pageSize) {
        PageUtil<OrderVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<OrderVO> orderVOList = new ArrayList<>();
        List<Order> orderList = orderMapper.selectListByParam(orderNo, orderSc, userId, paymentType, status, deleteStatus, sortEnum.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(orderList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(orderVOList);
            return pageUtil;
        }

        total = orderMapper.countByParam(orderNo, orderSc, userId, paymentType, status, deleteStatus);
        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        /* List to Map */
        Map<Long, List<OrderItemVO>> orderItemVOListMap = orderItemMapper.selectListByOrderNoSet(orderNoSet).stream()
                .map(this::orderItem2OrderItemVO).collect(Collectors.groupingBy(OrderItemVO::getOrderNo));
        Map<Long, OrderShippingAddressVO> orderShippingAddressVOMap = orderShippingAddressMapper.selectListByOrderNoSet(orderNoSet).stream()
                .map(this::orderShippingAddress2OrderShippingAddressVO).collect(Collectors.toMap(OrderShippingAddressVO::getOrderNo, v -> v));
        Map<Long, OrderLogisticsInfoVO> orderLogisticsInfoVOMap = orderLogisticsInfoMapper.selectListByOrderNoSet(orderNoSet).stream()
                .map(this::orderLogisticsInfo2OrderLogisticsInfoVO).collect(Collectors.toMap(OrderLogisticsInfoVO::getOrderNo, v -> v));

        orderList.stream().map(this::order2OrderVO).collect(Collectors.toList()).forEach(orderVO -> {
            orderVO.setShippingAddress(orderShippingAddressVOMap.get(orderVO.getOrderNo()));
            orderVO.setLogisticsInfo(orderLogisticsInfoVOMap.get(orderVO.getOrderNo()));
            orderVO.setOrderItemList(orderItemVOListMap.get(orderVO.getOrderNo()));
            orderVOList.add(orderVO);
        });
        pageUtil.setTotal(total);
        pageUtil.setList(orderVOList);
        return pageUtil;
    }

    @Override
    public OrderVO getOrderVO(Long orderNo) {
        OrderVO orderVO = new OrderVO();
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, null, null, Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            return orderVO;
        }

        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNo(orderNo);
        OrderShippingAddress orderShippingAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoMapper.selectByOrderNo(orderNo);

        List<OrderItemVO> orderItemVOList = orderItemList.stream().map(this::orderItem2OrderItemVO).collect(Collectors.toList());
        OrderShippingAddressVO orderShippingAddressVO = orderShippingAddress2OrderShippingAddressVO(orderShippingAddress);
        OrderLogisticsInfoVO orderLogisticsInfoVO = orderLogisticsInfo2OrderLogisticsInfoVO(orderLogisticsInfo);

        BeanUtils.copyProperties(order, orderVO);
        orderVO.setOrderItemList(orderItemVOList);
        orderVO.setShippingAddress(orderShippingAddressVO);
        orderVO.setLogisticsInfo(orderLogisticsInfoVO);
        return orderVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOrderLogisticsInfo(Long orderNo, LogisticsInfoForm form) {
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, null,
                OrderStatusEnum.PAY_SUCCESS.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单号:" + orderNo + ",订单不满足发货条件");
        }

        order.setStatus(OrderStatusEnum.NOT_RECEIPT.getCode());
        int row = orderMapper.updateByPrimaryKeySelective(order);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单状态修改失败");
        }

        OrderLogisticsInfo orderLogisticsInfo = new OrderLogisticsInfo();
        orderLogisticsInfo.setOrderNo(orderNo);
        orderLogisticsInfo.setLogisticsNo(form.getLogisticsNo());
        orderLogisticsInfo.setLogisticsTitle(form.getLogisticsTitle());
        return orderLogisticsInfoMapper.insertSelective(orderLogisticsInfo);
    }

    @Override
    public int updateOrderLogisticsInfo(Long orderNo, LogisticsInfoForm form) {
        OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoMapper.selectByOrderNo(orderNo);
        orderLogisticsInfo.setLogisticsNo(form.getLogisticsNo());
        orderLogisticsInfo.setLogisticsTitle(form.getLogisticsTitle());
        return orderLogisticsInfoMapper.updateByPrimaryKeySelective(orderLogisticsInfo);
    }

    private OrderShippingAddressVO orderShippingAddress2OrderShippingAddressVO(OrderShippingAddress orderShippingAddress) {
        OrderShippingAddressVO orderShippingAddressVO = new OrderShippingAddressVO();
        BeanUtils.copyProperties(orderShippingAddress, orderShippingAddressVO);
        return orderShippingAddressVO;
    }

    private OrderLogisticsInfoVO orderLogisticsInfo2OrderLogisticsInfoVO(OrderLogisticsInfo orderLogisticsInfo) {
        OrderLogisticsInfoVO orderLogisticsInfoVO = new OrderLogisticsInfoVO();
        BeanUtils.copyProperties(orderLogisticsInfo, orderLogisticsInfoVO);
        return orderLogisticsInfoVO;
    }

    private OrderVO order2OrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        return orderVO;
    }

    private OrderItemVO orderItem2OrderItemVO(OrderItem orderItem) {
        OrderItemVO orderItemVO = new OrderItemVO();
        BeanUtils.copyProperties(orderItem, orderItemVO);
        return orderItemVO;
    }

}
