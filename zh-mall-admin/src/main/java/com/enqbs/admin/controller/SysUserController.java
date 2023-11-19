package com.enqbs.admin.controller;

import com.enqbs.admin.form.ChangeNicknameForm;
import com.enqbs.admin.form.ChangePasswordForm;
import com.enqbs.admin.form.LoginForm;
import com.enqbs.admin.form.RegisterForm;
import com.enqbs.admin.form.SysRelationshipBindingForm;
import com.enqbs.admin.service.user.SysUserRoleService;
import com.enqbs.admin.service.user.SysUserService;
import com.enqbs.admin.vo.SysUserInfoVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.R;
import com.enqbs.security.service.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/login")
    public R<Map<String, String>> login(@Valid @RequestBody LoginForm form) {
        String token = sysUserService.login(form);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", token);
        return R.ok("登录成功", resultMap);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('SYS_USER:ADD')")
    public R<Map<String, Integer>> register(@Valid @RequestBody RegisterForm form) {
        Integer userId = sysUserService.register(form);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
        return R.ok("注册成功", resultMap);
    }

    @GetMapping("/user/info")
    public R<Map<String, Object>> userInfo(@RequestHeader String token) throws Exception {
        String newToken = tokenService.refreshToken(token).get();
        SysUserInfoVO sysUserInfo = sysUserService.getSysUserInfoVO();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", sysUserInfo);
        resultMap.put("token", newToken);
        return R.ok(resultMap);
    }

    @PutMapping("/change-password")
    public R<Void> changePassword(@Valid @RequestBody ChangePasswordForm form) {
        sysUserService.changePassword(form);
        return R.ok("修改密码成功");
    }

    @PutMapping("/change-nickname")
    public R<Void> changeNickname(@Valid @RequestBody ChangeNicknameForm form) {
        sysUserService.changeNickname(form);
        return R.ok("修改昵称成功");
    }

    @PostMapping("/sign-out")
    public R<Void> logout() {
        sysUserService.logout();
        return R.ok("退出成功");
    }

    @PostMapping("/user/bind")
    @PreAuthorize("hasAuthority('SYS_USER:UPDATE')")
    public R<Void> userRoleBind(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = sysUserRoleService.batchInsertUserRole(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("绑定角色失败");
        }

        return R.ok("绑定角色成功");
    }

    @PutMapping("/user/bind")
    @PreAuthorize("hasAuthority('SYS_USER:UPDATE')")
    public R<Void> updateUserRole(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = sysUserRoleService.updateUserRole(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("修改角色失败");
        }

        return R.ok("修改角色成功");
    }

    @DeleteMapping("/user/bind")
    @PreAuthorize("hasAuthority('SYS_USER:DELETE')")
    public R<Void> deleteUserRole(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = sysUserRoleService.deleteUserRole(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("删除角色失败");
        }

        return R.ok("删除角色成功");
    }

}
