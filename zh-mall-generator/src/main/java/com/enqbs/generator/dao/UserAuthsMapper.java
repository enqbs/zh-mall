package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.UserAuths;
import org.apache.ibatis.annotations.Param;

public interface UserAuthsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserAuths record);

    int insertSelective(UserAuths record);

    UserAuths selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuths record);

    int updateByPrimaryKey(UserAuths record);

    UserAuths selectByParam(@Param("userId") Integer userId,
                            @Param("identifier") String identifier,
                            @Param("credential") String credential);

    int countByParam(@Param("userId") Integer userId,
                     @Param("identifier") String identifier,
                     @Param("credential") String credential);

}
