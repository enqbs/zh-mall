package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.UserLevel;

public interface UserLevelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserLevel record);

    int insertSelective(UserLevel record);

    UserLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLevel record);

    int updateByPrimaryKey(UserLevel record);

}
