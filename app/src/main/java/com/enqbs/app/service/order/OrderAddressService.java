package com.enqbs.app.service.order;

import com.enqbs.app.pojo.vo.OrderAddressVO;
import com.enqbs.generator.pojo.OrderShippingAddress;

public interface OrderAddressService {

    /*
    * 订单收货地址快照
    * */
    OrderAddressVO getAddressVO(Long orderNo);

    /*
    * insert
    * */
    void insert(Long orderNo, OrderShippingAddress orderAddress);

}
