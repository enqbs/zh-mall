package com.enqbs.app.service;

import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.pojo.vo.UserInfoVO;

import java.util.Map;

public interface UserService {

    /*
     * 登录
     * */
    Map<String, Object> login(LoginForm form);

    /*
     * 注册
     * */
    Map<String, Object> registerByUsername(RegisterByUsernameForm form);

    /*
     * 获取用户信息
     * */
    UserInfoVO getUserInfoVO();

    /*
     * 退出登录
     * */
    void logout();

}
