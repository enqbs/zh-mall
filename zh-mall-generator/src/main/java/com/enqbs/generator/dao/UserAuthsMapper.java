package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.UserAuths;

public interface UserAuthsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserAuths record);

    int insertSelective(UserAuths record);

    UserAuths selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuths record);

    int updateByPrimaryKey(UserAuths record);

    UserAuths selectByIdentifier(String identifier);

    int countIdByIdentifier(String identifier);

}
