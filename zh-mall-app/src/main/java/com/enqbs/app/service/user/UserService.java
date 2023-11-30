package com.enqbs.app.service.user;

import com.enqbs.app.form.ChangeNicknameForm;
import com.enqbs.app.form.ChangePasswordForm;
import com.enqbs.app.form.ChangePhotoForm;
import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.security.pojo.LoginUser;

public interface UserService {

    /*
     * 登录
     * */
    String login(LoginForm form);

    /*
     * 登录（支付宝电脑网站扫码）
     * */
    String loginByAliPayPC(String code);

    /*
     * 注册
     * */
    Integer registerByUsername(RegisterByUsernameForm form);

    /*
     * 获取用户信息
     * */
    UserInfoVO getUserInfoVO();

    /*
     * 保存 OAuth2.0 授权用户信息
     * */
    LoginUser insert(String credential, String nickName, String photo);

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
