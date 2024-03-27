package com.enqbs.admin.service.sys;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.ChangeNicknameForm;
import com.enqbs.admin.form.ChangePasswordForm;
import com.enqbs.admin.form.LoginForm;
import com.enqbs.admin.form.RegisterForm;
import com.enqbs.admin.vo.SysUserInfoVO;
import com.enqbs.common.util.PageUtil;

public interface UserService {

    /*
     * 系统用户列表
     * */
    PageUtil<SysUserInfoVO> userInfoVOListPage(Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize);

    /*
     * 登录
     * */
    String login(LoginForm form);

    /*
     * 注册
     * */
    Integer register(RegisterForm form);

    /*
     * 获取用户信息
     * */
    SysUserInfoVO getUserInfoVO();

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
