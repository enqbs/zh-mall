package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderNo(Long orderNo);

    List<Order> selectListByParam(@Param("orderNo") Long orderNo,
                                  @Param("orderSc") String orderSc,
                                  @Param("userId") Integer userId,
                                  @Param("paymentType") Integer paymentType,
                                  @Param("status") Integer status,
                                  @Param("deleteStatus") Integer deleteStatus,
                                  @Param("sort") String sort,
                                  @Param("pageNum") Integer pageNum,
                                  @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("orderNo") Long orderNo,
                      @Param("orderSc") String orderSc,
                      @Param("userId") Integer userId,
                      @Param("paymentType") Integer paymentType,
                      @Param("status") Integer status,
                      @Param("deleteStatus") Integer deleteStatus);

}
