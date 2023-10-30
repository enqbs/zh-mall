package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.OrderShippingAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface OrderShippingAddressMapper {

    int deleteByPrimaryKey(Long orderNo);

    int insert(OrderShippingAddress record);

    int insertSelective(OrderShippingAddress record);

    OrderShippingAddress selectByPrimaryKey(Long orderNo);

    int updateByPrimaryKeySelective(OrderShippingAddress record);

    int updateByPrimaryKey(OrderShippingAddress record);

    List<OrderShippingAddress> selectListByOrderNoSet(@Param("orderNoSet") Set<Long> orderNoSet);

}
