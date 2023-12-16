package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.UserShippingAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserShippingAddressMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserShippingAddress record);

    int insertSelective(UserShippingAddress record);

    UserShippingAddress selectByPrimaryKey(Integer id);

    UserShippingAddress selectByPrimaryKeyOrUserIdOrDeleteStatus(@Param("id") Integer id,
                                                                 @Param("userId") Integer userId,
                                                                 @Param("deleteStatus") Integer deleteStatus);

    int updateByPrimaryKeySelective(UserShippingAddress record);

    int updateByPrimaryKey(UserShippingAddress record);

    List<UserShippingAddress> selectListByUserId(Integer userId);

    Integer existByPrimaryKeyAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

}
