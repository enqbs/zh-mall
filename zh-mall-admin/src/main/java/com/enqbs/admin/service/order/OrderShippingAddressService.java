package com.enqbs.admin.service.order;

import com.enqbs.admin.vo.OrderShippingAddressVO;

import java.util.List;
import java.util.Set;

public interface OrderShippingAddressService {

    List<OrderShippingAddressVO> getOrderShippingAddressVOList(Set<Long> orderNoSet);

    OrderShippingAddressVO getOrderShippingAddressVO(Long orderNo);

}
