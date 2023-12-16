package com.enqbs.app.service.user;

import com.enqbs.generator.pojo.UserLevel;

public interface UserLevelService {

    /*
     * 获取用户会员等级信息
     * */
    UserLevel getUserLevel(Integer userLevelId);

}
