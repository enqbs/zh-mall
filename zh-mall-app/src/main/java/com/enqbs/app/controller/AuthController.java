package com.enqbs.app.controller;

import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.service.user.UserService;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
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

    @PostMapping("/register/username")
    public R<Map<String, Integer>> register(@Valid @RequestBody RegisterByUsernameForm form) {
        Integer userId = userService.registerByUsername(form);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
        return R.ok("注册成功", resultMap);
    }

    @PostMapping("/sign-out")
    public R<Void> logout() {
        userService.logout();
        return R.ok("退出成功");
    }

}
