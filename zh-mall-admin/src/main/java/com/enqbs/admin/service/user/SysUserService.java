package com.enqbs.admin.service.user;

import com.enqbs.admin.form.ChangeNicknameForm;
import com.enqbs.admin.form.ChangePasswordForm;
import com.enqbs.admin.form.LoginForm;
import com.enqbs.admin.form.RegisterForm;
import com.enqbs.admin.vo.SysUserInfoVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

public interface SysUserService {

    /*
    * 系统用户列表
    * */
    PageUtil<SysUserInfoVO> getSysUserInfoVOList(Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize);

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
