package com.enqbs.admin.service.order;

import com.enqbs.admin.vo.OrderAddressVO;

import java.util.List;
import java.util.Set;

public interface OrderAddressService {

    List<OrderAddressVO> getOrderAddressVOList(Set<Long> orderNoSet);

    OrderAddressVO getOrderAddressVO(Long orderNo);

}
