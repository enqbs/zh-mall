package com.enqbs.app.service.order;

import com.enqbs.app.pojo.vo.OrderAddressVO;
import com.enqbs.generator.pojo.OrderShippingAddress;

public interface OrderAddressService {

    OrderAddressVO getAddressVO(Long orderNo);

    void insert(Long orderNo, OrderShippingAddress orderAddress);

}
