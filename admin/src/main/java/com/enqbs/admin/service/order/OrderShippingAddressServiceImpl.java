package com.enqbs.admin.service.order;

import com.enqbs.admin.convert.OrderConvert;
import com.enqbs.admin.vo.OrderShippingAddressVO;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderShippingAddressServiceImpl implements OrderShippingAddressService {

    @Resource
    private OrderShippingAddressMapper orderShippingAddressMapper;

    @Resource
    private OrderConvert orderConvert;

    @Override
    public List<OrderShippingAddressVO> getOrderShippingAddressVOList(Set<Long> orderNoSet) {
        List<OrderShippingAddress> orderShippingAddressList = orderShippingAddressMapper.selectListByOrderNoSet(orderNoSet);
        return orderShippingAddressList.stream()
                .map(e -> orderConvert.orderShippingAddress2OrderShippingAddressVO(e)).collect(Collectors.toList());
    }

    @Override
    public OrderShippingAddressVO getOrderShippingAddressVO(Long orderNo) {
        OrderShippingAddress orderShippingAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        return orderConvert.orderShippingAddress2OrderShippingAddressVO(orderShippingAddress);
    }

}