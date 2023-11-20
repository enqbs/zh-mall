package com.enqbs.app.service.user;

import com.enqbs.generator.dao.UserAuthsMapper;
import com.enqbs.generator.pojo.UserAuths;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserAuthsServiceImpl implements UserAuthsService {

    @Resource
    private UserAuthsMapper userAuthsMapper;

    @Override
    public UserAuths getUserAuths(Integer userId, String identifier, String credential) {
        return userAuthsMapper.selectByParam(userId, identifier, credential);
    }

    @Override
    public int countUserAuths(Integer userId, String identifier, String credential) {
        return userAuthsMapper.countByParam(userId, identifier, credential);
    }

    @Override
    public int insert(UserAuths userAuths) {
        return userAuthsMapper.insertSelective(userAuths);
    }

}
