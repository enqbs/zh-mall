package com.enqbs.admin.service.order;

import com.enqbs.admin.vo.OrderItemVO;

import java.util.List;
import java.util.Set;

public interface OrderItemService {

    List<OrderItemVO> getOrderItemVOList(Long orderNo);

    List<OrderItemVO> getOrderItemVOList(Set<Long> orderNoSet);

}
