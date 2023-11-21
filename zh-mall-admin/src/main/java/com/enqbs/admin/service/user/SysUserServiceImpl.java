package com.enqbs.admin.service.user;

import com.enqbs.admin.convert.SysUserConvert;
import com.enqbs.admin.form.ChangeNicknameForm;
import com.enqbs.admin.form.ChangePasswordForm;
import com.enqbs.admin.form.LoginForm;
import com.enqbs.admin.form.RegisterForm;
import com.enqbs.admin.vo.SysUserInfoVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.IDUtil;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.SysUserMapper;
import com.enqbs.generator.pojo.SysMenu;
import com.enqbs.generator.pojo.SysUser;
import com.enqbs.security.pojo.LoginUser;
import com.enqbs.security.service.TokenService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private TokenService tokenService;

    @Resource
    private SysUserConvert sysUserConvert;

    @Resource
    private ThreadPoolTaskExecutor executor;

    @Override
    public PageUtil<SysUserInfoVO> getSysUserInfoVOList(Integer deleteStatus, SortEnum sortEnum, Integer pageNum, Integer pageSize) {
        PageUtil<SysUserInfoVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<SysUser> sysUserList = sysUserMapper.selectListByParam(deleteStatus, sortEnum.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(sysUserList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(Collections.emptyList());
            return pageUtil;
        }

        total = sysUserMapper.countByParam(deleteStatus);
        List<SysUserInfoVO> sysUserInfoVOList = sysUserList.stream()
                .map(e -> sysUserConvert.sysUserInfo2SysUserInfoVO(e)).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(sysUserInfoVOList);
        return pageUtil;
    }

    @Override
    public String login(LoginForm form) {
        SysUser sysUser = sysUserMapper.selectByUsername(form.getUsername());

        if (ObjectUtils.isEmpty(sysUser)) {
            throw new ServiceException("用户不存在");
        }

        if (!passwordEncoder.matches(form.getPassword(), sysUser.getPassword())) {
            throw new ServiceException("密码错误");
        }

        Set<SysMenu> sysMenuSet = sysMenuService.getSysMenuSet(form.getUsername());
        List<String> permissionList = sysMenuSet.stream().map(SysMenu::getPermissionsKey).collect(Collectors.toList());
        LoginUser loginUser = getLoginUser(sysUser, permissionList);
        executor.execute(() -> cacheLoginUser(loginUser));
        return tokenService.getToken(loginUser);
    }

    @Override
    public Integer register(RegisterForm form) {
        int count = sysUserMapper.countByUsername(form.getUsername());

        if (count > 0) {
            throw new ServiceException("用户已存在");
        }

        SysUser sysUser = new SysUser();
        sysUser.setUsername(form.getUsername());
        sysUser.setPassword(passwordEncoder.encode(form.getPassword()));
        int row = sysUserMapper.insertSelective(sysUser);

        if (row <= 0) {
            throw new ServiceException("用户账号密码保存失败");
        }

        return sysUser.getId();
    }

    @Override
    public SysUserInfoVO getSysUserInfoVO() {
        LoginUser loginUser = tokenService.getLoginUser();
        return sysUserConvert.loginUser2SysUserInfoVO(loginUser);
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

        SysUser sysUser = new SysUser();
        sysUser.setId(loginUser.getUserId());
        sysUser.setPassword(passwordEncoder.encode(form.getNewPassword()));
        int row = sysUserMapper.updateByPrimaryKeySelective(sysUser);

        if (row <= 0) {
            throw new ServiceException("修改密码失败");
        }

        logout();
    }

    @Override
    public void changeNickname(ChangeNicknameForm form) {
        LoginUser loginUser = tokenService.getLoginUser();
        SysUser sysUser = new SysUser();
        sysUser.setId(loginUser.getUserId());
        sysUser.setNickName(form.getNickName());
        int row = sysUserMapper.updateByPrimaryKeySelective(sysUser);

        if (row <= 0) {
            throw new ServiceException("修改昵称失败");
        }

        executor.execute(() -> {
            loginUser.setNickName(form.getNickName());
            removeCacheLoginUser(loginUser);
            cacheLoginUser(loginUser);
        });
    }

    @Override
    public void logout() {
        LoginUser loginUser = tokenService.getLoginUser();
        executor.execute(() -> removeCacheLoginUser(loginUser));
        SecurityContextHolder.clearContext();
    }

    private LoginUser getLoginUser(SysUser sysUser, List<String> permissionList) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserToken(IDUtil.getUUID());
        loginUser.setUserType(Constants.SYS_USER_TOKEN);
        loginUser.setUserId(sysUser.getId());
        loginUser.setUsername(sysUser.getUsername());
        loginUser.setPassword(sysUser.getPassword());
        loginUser.setNickName(sysUser.getNickName());
        loginUser.setPhoto(sysUser.getPhoto());
        loginUser.setPermissionList(permissionList);
        return loginUser;
    }

    private void cacheLoginUser(LoginUser loginUser) {
        String redisKey = String.format(Constants.SYS_USER_REDIS_KEY, loginUser.getUserToken());
        long cacheTimeout = 3600 * 7 * 1000L;     // 用户信息 redis 缓存7天(免登录)
        redisUtil.setObject(redisKey, loginUser, cacheTimeout);
    }

    private void removeCacheLoginUser(LoginUser loginUser) {
        String redisKey = String.format(Constants.SYS_USER_REDIS_KEY, loginUser.getUserToken());
        redisUtil.deleteObject(redisKey);
    }

}
