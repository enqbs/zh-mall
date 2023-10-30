package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.OrderLogisticsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface OrderLogisticsInfoMapper {

    int deleteByPrimaryKey(@Param("orderNo") Long orderNo, @Param("logisticsNo") String logisticsNo);

    int insert(OrderLogisticsInfo record);

    int insertSelective(OrderLogisticsInfo record);

    OrderLogisticsInfo selectByPrimaryKey(@Param("orderNo") Long orderNo, @Param("logisticsNo") String logisticsNo);

    OrderLogisticsInfo selectByOrderNo(Long orderNo);

    int updateByPrimaryKeySelective(OrderLogisticsInfo record);

    int updateByPrimaryKey(OrderLogisticsInfo record);

    List<OrderLogisticsInfo> selectListByOrderNoSet(@Param("orderNoSet") Set<Long> orderNoSet);

}
