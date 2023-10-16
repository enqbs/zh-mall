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

    Long countByUserId(@Param("userId") Integer userId, @Param("status") Integer status);

    List<Order> selectListByParam(@Param("userId") Integer userId, @Param("status") Integer status,
                                  @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

}
