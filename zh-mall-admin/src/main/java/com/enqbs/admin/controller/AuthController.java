package com.enqbs.admin.controller;

import com.enqbs.admin.form.ChangeNicknameForm;
import com.enqbs.admin.form.ChangePasswordForm;
import com.enqbs.admin.form.LoginForm;
import com.enqbs.admin.form.RegisterForm;
import com.enqbs.admin.service.user.SysUserService;
import com.enqbs.common.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private SysUserService sysUserService;

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

    @PutMapping("/change/password")
    public R<Void> changePassword(@Valid @RequestBody ChangePasswordForm form) {
        sysUserService.changePassword(form);
        return R.ok("修改密码成功");
    }

    @PutMapping("/change/nickname")
    public R<Void> changeNickname(@Valid @RequestBody ChangeNicknameForm form) {
        sysUserService.changeNickname(form);
        return R.ok("修改昵称成功");
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        sysUserService.logout();
        return R.ok("退出成功");
    }

}
