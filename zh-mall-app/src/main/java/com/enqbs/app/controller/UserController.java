package com.enqbs.app.controller;

import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.service.UserService;
import com.enqbs.app.vo.UserInfoVO;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public R<Map<String, String>> login(@Valid @RequestBody LoginForm form) {
        Map<String, String> login = userService.login(form);
        return R.ok("登录成功", login);
    }

    @PostMapping("/register/username")
    public R<Map<String, Object>> register(@Valid @RequestBody RegisterByUsernameForm form) {
        Map<String, Object> register = userService.registerByUsername(form);
        return R.ok("注册成功", register);
    }

    @GetMapping("/user-info")
    public R<UserInfoVO> userInfo() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        return R.ok(userInfoVO);
    }

    @PostMapping("/sign-out")
    public R<Void> logout() {
        userService.logout();
        return R.ok("退出成功");
    }

}
