package com.enqbs.admin.service.order;

import com.enqbs.admin.vo.OrderShippingAddressVO;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderShippingAddressServiceImpl implements OrderShippingAddressService {

    @Resource
    private OrderShippingAddressMapper orderShippingAddressMapper;

    @Override
    public List<OrderShippingAddressVO> getOrderShippingAddressVOList(Set<Long> orderNoSet) {
        List<OrderShippingAddress> orderShippingAddressList = orderShippingAddressMapper.selectListByOrderNoSet(orderNoSet);
        return orderShippingAddressList.stream().map(this::orderShippingAddress2OrderShippingAddressVO).collect(Collectors.toList());
    }

    @Override
    public OrderShippingAddressVO getOrderShippingAddressVO(Long orderNo) {
        OrderShippingAddress orderShippingAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        return orderShippingAddress2OrderShippingAddressVO(orderShippingAddress);
    }

    private OrderShippingAddressVO orderShippingAddress2OrderShippingAddressVO(OrderShippingAddress orderShippingAddress) {
        OrderShippingAddressVO orderShippingAddressVO = new OrderShippingAddressVO();
        BeanUtils.copyProperties(orderShippingAddress, orderShippingAddressVO);
        return orderShippingAddressVO;
    }

}
