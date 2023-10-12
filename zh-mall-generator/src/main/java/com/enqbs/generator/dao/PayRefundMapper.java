package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.PayRefund;

public interface PayRefundMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PayRefund record);

    int insertSelective(PayRefund record);

    PayRefund selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PayRefund record);

    int updateByPrimaryKey(PayRefund record);

}
