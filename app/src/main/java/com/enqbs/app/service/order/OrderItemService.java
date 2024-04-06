package com.enqbs.app.service.order;

import com.enqbs.app.pojo.vo.OrderItemVO;
import com.enqbs.generator.pojo.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderItemService {

    /**
     * 获取订单项列表
     *
     * @param orderNo 订单号
     * @return 订单项列表
     */
    List<OrderItemVO> getOrderItemVOList(Long orderNo);

    /**
     * 获取订单项列表
     *
     * @param orderNoSet 订单号集合
     * @return 订单项列表
     */
    List<OrderItemVO> getOrderItemVOList(Set<Long> orderNoSet);

    /**
     * 批量插入订单项
     *
     * @param orderNo 订单号
     * @param orderItemList 订单项列表
     */
    void batchInsert(Long orderNo, List<OrderItem> orderItemList);

}
