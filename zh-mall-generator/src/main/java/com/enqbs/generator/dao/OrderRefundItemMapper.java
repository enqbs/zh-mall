package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.OrderRefundItem;
import org.apache.ibatis.annotations.Param;

public interface OrderRefundItemMapper {

    int deleteByPrimaryKey(@Param("orderNo") Long orderNo, @Param("skuId") Integer skuId);

    int insert(OrderRefundItem record);

    int insertSelective(OrderRefundItem record);

    OrderRefundItem selectByPrimaryKey(@Param("orderNo") Long orderNo, @Param("skuId") Integer skuId);

    int updateByPrimaryKeySelective(OrderRefundItem record);

    int updateByPrimaryKey(OrderRefundItem record);

}
