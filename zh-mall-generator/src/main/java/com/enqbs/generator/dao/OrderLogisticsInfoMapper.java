package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.OrderLogisticsInfo;
import org.apache.ibatis.annotations.Param;

public interface OrderLogisticsInfoMapper {

    int deleteByPrimaryKey(@Param("orderNo") Long orderNo, @Param("logisticsNo") String logisticsNo);

    int insert(OrderLogisticsInfo record);

    int insertSelective(OrderLogisticsInfo record);

    OrderLogisticsInfo selectByPrimaryKey(@Param("orderNo") Long orderNo, @Param("logisticsNo") String logisticsNo);

    int updateByPrimaryKeySelective(OrderLogisticsInfo record);

    int updateByPrimaryKey(OrderLogisticsInfo record);

}
