package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.PayPlatform;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PayPlatformMapper {

    int deleteByPrimaryKey(@Param("payInfoId") Long payInfoId, @Param("orderNo") Long orderNo);

    int insert(PayPlatform record);

    int insertSelective(PayPlatform record);

    PayPlatform selectByPrimaryKey(@Param("payInfoId") Long payInfoId, @Param("orderNo") Long orderNo);

    PayPlatform selectByPayInfoId(Long payInfoId);

    int updateByPrimaryKeySelective(PayPlatform record);

    int updateByPrimaryKey(PayPlatform record);

    List<PayPlatform> selectListByPayInfoIdSet(@Param("payInfoIdSet") Set<Long> payInfoIdSet);

}
