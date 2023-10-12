package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.OrderRefund;

public interface OrderRefundMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderRefund record);

    int insertSelective(OrderRefund record);

    OrderRefund selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderRefund record);

    int updateByPrimaryKey(OrderRefund record);

}
