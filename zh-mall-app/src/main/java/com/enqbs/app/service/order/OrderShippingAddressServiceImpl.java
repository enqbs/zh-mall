package com.enqbs.app.service.order;

import com.enqbs.app.pojo.vo.OrderShippingAddressVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderShippingAddressServiceImpl implements OrderShippingAddressService {

    @Resource
    private OrderShippingAddressMapper orderShippingAddressMapper;

    @Override
    public OrderShippingAddressVO getOrderShippingAddressVO(Long orderNo) {
        OrderShippingAddress orderShippingAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        return orderShippingAddress2OrderShippingAddressVO(orderShippingAddress);
    }

    @Override
    public void insert(Long orderNo, OrderShippingAddress orderShippingAddress) {
        int row = orderShippingAddressMapper.insertSelective(orderShippingAddress);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单收货地址保存失败");
        }
    }

    private OrderShippingAddressVO orderShippingAddress2OrderShippingAddressVO(OrderShippingAddress orderShippingAddress) {
        OrderShippingAddressVO orderShippingAddressVO = new OrderShippingAddressVO();
        BeanUtils.copyProperties(orderShippingAddress, orderShippingAddressVO);
        return orderShippingAddressVO;
    }

}
