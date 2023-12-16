package com.enqbs.app.service.order;

import com.enqbs.app.pojo.vo.OrderShippingAddressVO;
import com.enqbs.generator.pojo.OrderShippingAddress;

public interface OrderShippingAddressService {

    OrderShippingAddressVO getOrderShippingAddressVO(Long orderNo);

    void insert(Long orderNo, OrderShippingAddress orderShippingAddress);

}
