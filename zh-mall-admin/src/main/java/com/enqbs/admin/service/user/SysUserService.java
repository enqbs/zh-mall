package com.enqbs.admin.service.user;

import com.enqbs.admin.form.ChangeNicknameForm;
import com.enqbs.admin.form.ChangePasswordForm;
import com.enqbs.admin.form.LoginForm;
import com.enqbs.admin.form.RegisterForm;
import com.enqbs.admin.vo.SysUserInfoVO;

import java.util.Map;

public interface SysUserService {

    /*
     * 登录
     * */
    Map<String, Object> login(LoginForm form);

    /*
     * 注册
     * */
    Map<String, Object> register(RegisterForm form);

    /*
     * 获取用户信息
     * */
    SysUserInfoVO getSysUserInfoVO();

    /*
     * 修改密码
     * */
    void changePassword(ChangePasswordForm form);

    /*
    * 修改昵称
    * */
    void changeNickname(ChangeNicknameForm form);

    /*
     * 退出登录
     * */
    void logout();

}
