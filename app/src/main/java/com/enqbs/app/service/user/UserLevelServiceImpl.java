package com.enqbs.app.service.user;

import com.enqbs.generator.dao.UserLevelMapper;
import com.enqbs.generator.pojo.UserLevel;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserLevelServiceImpl implements UserLevelService {

    @Resource
    private UserLevelMapper userLevelMapper;

    @Override
    public UserLevel getUserLevel(Integer userLevelId) {
        return userLevelMapper.selectByPrimaryKey(userLevelId);
    }

}
