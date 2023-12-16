package com.enqbs.app.service.user;

import com.enqbs.generator.dao.UserLevelMapper;
import com.enqbs.generator.pojo.UserLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserLevelServiceImpl implements UserLevelService {

    @Resource
    private UserLevelMapper userLevelMapper;

    @Override
    public UserLevel getUserLevel(Integer userLevelId) {
        return userLevelMapper.selectByPrimaryKey(userLevelId);
    }

}
