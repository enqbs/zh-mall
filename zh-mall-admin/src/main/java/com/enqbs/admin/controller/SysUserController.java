package com.enqbs.admin.controller;

import com.enqbs.admin.form.ChangePasswordForm;
import com.enqbs.admin.form.LoginForm;
import com.enqbs.admin.form.RegisterForm;
import com.enqbs.admin.service.user.SysUserService;
import com.enqbs.admin.vo.SysUserInfoVO;
import com.enqbs.common.util.R;
import com.enqbs.security.service.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private TokenService tokenService;

    @PostMapping("/login")
    public R<Map<String, Object>> login(@Valid @RequestBody LoginForm form) {
        Map<String, Object> resultMap = sysUserService.login(form);
        return R.ok("登录成功", resultMap);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('SYS_USER:ADD')")
    public R<Map<String, Object>> register(@Valid @RequestBody RegisterForm form) {
        Map<String, Object> resultMap = sysUserService.register(form);
        return R.ok("注册成功", resultMap);
    }

    @GetMapping("/user-info")
    public R<Map<String, Object>> userInfo(@RequestHeader String token) throws Exception {
        String newToken = tokenService.refreshToken(token).get();
        SysUserInfoVO sysUserInfoVO = sysUserService.getSysUserInfoVO();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", sysUserInfoVO);
        resultMap.put("token", newToken);
        return R.ok(resultMap);
    }

    @PutMapping("/change-password")
    public R<Void> changePassword(@Valid @RequestBody ChangePasswordForm form) {
        sysUserService.changePassword(form);
        return R.ok("修改密码成功");
    }

    @PostMapping("/sign-out")
    public R<Void> logout() {
        sysUserService.logout();
        return R.ok("退出成功");
    }

}
