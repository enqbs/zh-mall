package com.enqbs.app.service.order;

import com.enqbs.app.pojo.vo.OrderItemVO;
import com.enqbs.generator.pojo.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderItemService {

    List<OrderItemVO> getOrderItemVOList(Long orderNo);

    List<OrderItemVO> getOrderItemVOList(Set<Long> orderNoSet);

    void batchInsert(Long orderNo, List<OrderItem> orderItemList);

}
