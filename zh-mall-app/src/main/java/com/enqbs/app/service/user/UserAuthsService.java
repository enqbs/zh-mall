package com.enqbs.app.service.user;

import com.enqbs.generator.pojo.UserAuths;

public interface UserAuthsService {

    UserAuths getUserAuths(Integer userId, String identifier, String credential);

    int countUserAuths(Integer userId, String identifier, String credential);

    int insert(UserAuths userAuths);

    int update(UserAuths userAuths);

}
