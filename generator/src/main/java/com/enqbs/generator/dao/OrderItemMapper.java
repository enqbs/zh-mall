package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface OrderItemMapper {

    int deleteByPrimaryKey(@Param("orderNo") Long orderNo, @Param("skuId") Integer skuId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(@Param("orderNo") Long orderNo, @Param("skuId") Integer skuId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int batchInsertByOrderItemList(@Param("orderItemList") List<OrderItem> orderItemList);

    List<OrderItem> selectListByOrderNo(Long orderNo);

    List<OrderItem> selectListByOrderNoSet(@Param("orderNoSet") Set<Long> orderNoSet);

}
