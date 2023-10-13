package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.UserShippingAddress;

import java.util.List;

public interface UserShippingAddressMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserShippingAddress record);

    int insertSelective(UserShippingAddress record);

    UserShippingAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserShippingAddress record);

    int updateByPrimaryKey(UserShippingAddress record);

    List<UserShippingAddress> selectListByUserId(Integer userId);

}
