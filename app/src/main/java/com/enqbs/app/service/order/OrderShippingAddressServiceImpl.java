package com.enqbs.app.service.order;

import com.enqbs.app.convert.OrderConvert;
import com.enqbs.app.pojo.vo.OrderShippingAddressVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderShippingAddressServiceImpl implements OrderShippingAddressService {

    @Resource
    private OrderShippingAddressMapper orderShippingAddressMapper;

    @Resource
    private OrderConvert orderConvert;

    @Override
    public OrderShippingAddressVO getOrderShippingAddressVO(Long orderNo) {
        OrderShippingAddress orderShippingAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        return ObjectUtils.isEmpty(orderShippingAddress) ? null : orderConvert.orderShippingAddress2OrderShippingAddressVO(orderShippingAddress);
    }

    @Override
    public void insert(Long orderNo, OrderShippingAddress orderShippingAddress) {
        int row = orderShippingAddressMapper.insertSelective(orderShippingAddress);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单收货地址保存失败");
        }
    }

}
