package com.enqbs.app.service.user;

import com.enqbs.app.form.ChangeNicknameForm;
import com.enqbs.app.form.ChangePasswordForm;
import com.enqbs.app.form.ChangePhotoForm;
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
     * 用户修改密码
     * */
    void changePassword(ChangePasswordForm form);

    /*
     * 用户修改昵称
     * */
    void changeNickname(ChangeNicknameForm form);

    /*
     * 用户修改头像
     * */
    void changePhoto(ChangePhotoForm form);

    /*
     * 退出登录
     * */
    void logout();

}
