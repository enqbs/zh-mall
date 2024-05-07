package com.enqbs.app.service.user;

import com.enqbs.generator.pojo.UserAuths;

public interface UserAuthsService {

    /**
     * 获取用户标识
     *
     * @param userId 用户 ID
     * @param identifier identifier
     * @param credential credential
     * @return 用户标识信息
     */
    UserAuths getUserAuths(Integer userId, String identifier, String credential);

    /**
     * 判断用户是否存在
     *
     * @param userId 用户 ID
     * @param identifier identifier
     * @param credential credential
     * @return row
     */
    Integer exist(Integer userId, String identifier, String credential);

    int insert(UserAuths userAuths);

    int update(UserAuths userAuths);

}
