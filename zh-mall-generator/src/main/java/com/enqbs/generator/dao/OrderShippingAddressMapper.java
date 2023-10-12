package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.OrderShippingAddress;

public interface OrderShippingAddressMapper {

    int deleteByPrimaryKey(Long orderNo);

    int insert(OrderShippingAddress record);

    int insertSelective(OrderShippingAddress record);

    OrderShippingAddress selectByPrimaryKey(Long orderNo);

    int updateByPrimaryKeySelective(OrderShippingAddress record);

    int updateByPrimaryKey(OrderShippingAddress record);

}
