package com.enqbs.app.service.user;

import com.enqbs.generator.dao.UserAuthsMapper;
import com.enqbs.generator.pojo.UserAuths;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserAuthsServiceImpl implements UserAuthsService {

    @Resource
    private UserAuthsMapper userAuthsMapper;

    @Override
    public UserAuths getUserAuths(Integer userId, String identifier, String credential) {
        return userAuthsMapper.selectByParam(userId, identifier, credential);
    }

    @Override
    public Integer exist(Integer userId, String identifier, String credential) {
        return userAuthsMapper.existByParam(userId, identifier, credential);
    }

    @Override
    public int insert(UserAuths userAuths) {
        return userAuthsMapper.insertSelective(userAuths);
    }

    @Override
    public int update(UserAuths userAuths) {
        return userAuthsMapper.updateByPrimaryKeySelective(userAuths);
    }

}
