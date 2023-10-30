package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.UserLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserLevelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserLevel record);

    int insertSelective(UserLevel record);

    UserLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLevel record);

    int updateByPrimaryKey(UserLevel record);

    List<UserLevel> selectListByIdSet(@Param("idSet") Set<Integer> idSet);

}
