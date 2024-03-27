package com.enqbs.admin.service.order;

import com.enqbs.admin.convert.OrderConvert;
import com.enqbs.admin.vo.OrderAddressVO;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.OrderShippingAddress;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class OrderAddressServiceImpl implements OrderAddressService {

    @Resource
    private OrderShippingAddressMapper orderShippingAddressMapper;

    @Resource
    private OrderConvert orderConvert;

    @Override
    public List<OrderAddressVO> getOrderAddressVOList(Set<Long> orderNoSet) {
        List<OrderShippingAddress> orderAddressList = CollectionUtils.isEmpty(orderNoSet) ? Collections.emptyList() : orderShippingAddressMapper.selectListByOrderNoSet(orderNoSet);

        if (CollectionUtils.isEmpty(orderAddressList)) {
            return Collections.emptyList();
        }

        return orderAddressList.stream().map(o -> orderConvert.orderAddress2OrderAddressVO(o)).toList();
    }

    @Override
    public OrderAddressVO getOrderAddressVO(Long orderNo) {
        OrderShippingAddress orderAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        return orderConvert.orderAddress2OrderAddressVO(orderAddress);
    }

}
