package com.enqbs.app.service.order;

import com.enqbs.app.convert.OrderConvert;
import com.enqbs.app.pojo.vo.OrderAddressVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.OrderShippingAddress;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderAddressServiceImpl implements OrderAddressService {

    @Resource
    private OrderShippingAddressMapper orderShippingAddressMapper;

    @Resource
    private OrderConvert orderConvert;

    @Override
    public OrderAddressVO getAddressVO(Long orderNo) {
        OrderShippingAddress address = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        return orderConvert.address2AddressVO(address);
    }

    @Override
    public void insert(Long orderNo, OrderShippingAddress orderAddress) {
        int row = orderShippingAddressMapper.insertSelective(orderAddress);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单收货地址保存失败");
        }
    }

}
