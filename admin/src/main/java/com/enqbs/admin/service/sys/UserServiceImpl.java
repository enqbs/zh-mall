package com.enqbs.admin.service.sys;

import com.enqbs.admin.convert.SysConvert;
import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.ChangeNicknameForm;
import com.enqbs.admin.form.ChangePasswordForm;
import com.enqbs.admin.form.LoginForm;
import com.enqbs.admin.form.RegisterForm;
import com.enqbs.admin.vo.SysUserInfoVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.IDUtil;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.SysUserMapper;
import com.enqbs.generator.pojo.SysMenu;
import com.enqbs.generator.pojo.SysUser;
import com.enqbs.security.pojo.LoginUser;
import com.enqbs.security.service.TokenService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private MenuService menuService;
    @Resource
    private TokenService tokenService;
    @Resource
    private SysConvert sysConvert;

    @Override
    public PageUtil<SysUserInfoVO> userInfoVOListPage(Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize) {
        Long total = sysUserMapper.countByParam(deleteStatus);
        List<SysUser> userList = sysUserMapper.selectListByParam(deleteStatus, sort.getSortType(), pageNum, pageSize);
        PageUtil<SysUserInfoVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(userList.stream().map(u -> sysConvert.user2UserInfoVO(u)).toList());
        return pageUtil;
    }

    @Override
    public String login(LoginForm form) {
        SysUser user = sysUserMapper.selectByUsername(form.getUsername());

        if (ObjectUtils.isEmpty(user)) {
            throw new ServiceException("用户不存在");
        }

        if (!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            throw new ServiceException("密码错误");
        }

        Set<SysMenu> menuSet = menuService.getMenuSet(form.getUsername());
        List<String> permissionList = menuSet.stream().map(SysMenu::getPermissionsKey).toList();
        LoginUser loginUser = getLoginUser(user, permissionList);
        Thread.ofVirtual().name("login-cacheLoginUser").start(() -> cacheLoginUser(loginUser));
        return tokenService.getToken(loginUser);
    }

    @Override
    public Integer register(RegisterForm form) {
        int exist = sysUserMapper.existByUsername(form.getUsername());

        if (ObjectUtils.isNotEmpty(exist)) {
            throw new ServiceException("用户已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        int row = sysUserMapper.insertSelective(user);

        if (row <= 0) {
            throw new ServiceException("用户账号密码保存失败");
        }

        return user.getId();
    }

    @Override
    public SysUserInfoVO getUserInfoVO() {
        LoginUser loginUser = tokenService.getLoginUser();
        return sysConvert.loginUser2UserInfoVO(loginUser);
    }

    @Override
    public void changePassword(ChangePasswordForm form) {
        LoginUser loginUser = tokenService.getLoginUser();

        if (!passwordEncoder.matches(form.getOldPassword(), loginUser.getPassword())) {
            throw new ServiceException("密码错误");
        }

        if (!form.getNewPassword().equals(form.getNewPasswordAgain())) {
            throw new ServiceException("两次输入的密码不一样");
        }

        SysUser user = new SysUser();
        user.setId(loginUser.getUserId());
        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        int row = sysUserMapper.updateByPrimaryKeySelective(user);

        if (row <= 0) {
            throw new ServiceException("修改密码失败");
        }

        logout();
    }

    @Override
    public void changeNickname(ChangeNicknameForm form) {
        LoginUser loginUser = tokenService.getLoginUser();
        SysUser user = new SysUser();
        user.setId(loginUser.getUserId());
        user.setNickName(form.getNickName());
        int row = sysUserMapper.updateByPrimaryKeySelective(user);

        if (row <= 0) {
            throw new ServiceException("修改昵称失败");
        }

        Thread.ofVirtual().name("changeNickname-updateLoginUser").start(() -> {
                    loginUser.setNickName(form.getNickName());
                    removeCacheLoginUser(loginUser);
                    cacheLoginUser(loginUser);
                }
        );
    }

    @Override
    public void logout() {
        LoginUser loginUser = tokenService.getLoginUser();
        Thread.ofVirtual().name("logout-removeCacheLoginUser").start(() -> removeCacheLoginUser(loginUser));
        tokenService.removeLoginUser();
    }

    private LoginUser getLoginUser(SysUser user, List<String> permissionList) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserToken(IDUtil.getUUID());
        loginUser.setUserType(Constants.SYS_USER_TOKEN);
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword());
        loginUser.setNickName(user.getNickName());
        loginUser.setPhoto(user.getPhoto());
        loginUser.setPermissionList(permissionList);
        return loginUser;
    }

    private void cacheLoginUser(LoginUser loginUser) {
        String redisKey = String.format(Constants.SYS_USER_REDIS_KEY, loginUser.getUserToken());
        Long cacheTimeout = 3600 * 7 * 1000L;     // 用户信息 redis 缓存7天(免登录)
        redisUtil.setString(redisKey, GsonUtil.obj2Json(loginUser), cacheTimeout);
    }

    private void removeCacheLoginUser(LoginUser loginUser) {
        String redisKey = String.format(Constants.SYS_USER_REDIS_KEY, loginUser.getUserToken());
        redisUtil.deleteKey(redisKey);
    }

}
