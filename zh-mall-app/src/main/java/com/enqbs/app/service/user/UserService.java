package com.enqbs.app.service.user;

import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.pojo.vo.UserInfoVO;

public interface UserService {

    /*
     * 登录
     * */
    String login(LoginForm form);

    /*
     * 注册
     * */
    Integer registerByUsername(RegisterByUsernameForm form);

    /*
     * 获取用户信息
     * */
    UserInfoVO getUserInfoVO();

    /*
     * 退出登录
     * */
    void logout();

}
