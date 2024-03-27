package com.enqbs.admin.service.order;

import com.enqbs.admin.convert.OrderConvert;
import com.enqbs.admin.vo.OrderShippingAddressVO;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
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
        if (CollectionUtils.isEmpty(orderNoSet)) {
            return Collections.emptyList();
        }

        List<OrderShippingAddress> orderShippingAddressList = orderShippingAddressMapper.selectListByOrderNoSet(orderNoSet);
        return CollectionUtils.isEmpty(orderShippingAddressList) ?
                Collections.emptyList() : orderShippingAddressList.stream().map(o -> orderConvert.orderShippingAddress2OrderShippingAddressVO(o)).collect(Collectors.toList());
    }

    @Override
    public OrderShippingAddressVO getOrderShippingAddressVO(Long orderNo) {
        OrderShippingAddress orderShippingAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        return ObjectUtils.isEmpty(orderShippingAddress) ? null : orderConvert.orderShippingAddress2OrderShippingAddressVO(orderShippingAddress);
    }

}
