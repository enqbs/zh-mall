package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectListByParam(@Param("id") Integer id,
                                 @Param("uid") Long uid,
                                 @Param("identifier") String identifier,
                                 @Param("status") Integer status,
                                 @Param("deleteStatus") Integer deleteStatus,
                                 @Param("pageNum") Integer pageNum,
                                 @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("id") Integer id,
                      @Param("uid") Long uid,
                      @Param("identifier") String identifier,
                      @Param("status") Integer status,
                      @Param("deleteStatus") Integer deleteStatus);

}
