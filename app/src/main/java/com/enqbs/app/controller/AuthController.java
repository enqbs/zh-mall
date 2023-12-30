package com.enqbs.app.controller;

import com.enqbs.app.form.ChangeNicknameForm;
import com.enqbs.app.form.ChangePasswordForm;
import com.enqbs.app.form.ChangePhotoForm;
import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.service.user.UserService;
import com.enqbs.common.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public R<Map<String, String>> login(@Valid @RequestBody LoginForm form) {
        String token = userService.login(form);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", token);
        return R.ok("登录成功", resultMap);
    }

    @PostMapping("/login/alipay-page/{code}")
    public R<Map<String, String>> aliAuthPage(@PathVariable String code) {
        String token = userService.loginByAlipayPageAuth(code);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", token);
        return R.ok("登录成功", resultMap);
    }

    @PostMapping("/register/username")
    public R<Map<String, Integer>> register(@Valid @RequestBody RegisterByUsernameForm form) {
        Integer userId = userService.registerByUsername(form);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
        return R.ok("注册成功", resultMap);
    }

    @PutMapping("/change/password")
    public R<Void> changePassword(@Valid @RequestBody ChangePasswordForm form) {
        userService.changePassword(form);
        return R.ok("修改密码成功");
    }

    @PutMapping("/change/nickname")
    public R<Void> changeNickname(@Valid @RequestBody ChangeNicknameForm form) {
        userService.changeNickname(form);
        return R.ok("修改昵称成功");
    }

    @PutMapping("/change/photo")
    public R<Void> changePhoto(@Valid @RequestBody ChangePhotoForm form) {
        userService.changePhoto(form);
        return R.ok("修改头像成功");
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        userService.logout();
        return R.ok("退出成功");
    }

}
