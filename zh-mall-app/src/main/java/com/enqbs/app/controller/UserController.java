package com.enqbs.app.controller;

import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.service.UserService;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.common.util.R;
import com.enqbs.security.service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

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
    public R<Map<String, Object>> userInfo(@RequestHeader String token) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String newToken = tokenService.refreshToken(token);
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfoVO);
        map.put("token", newToken);
        return R.ok(map);
    }

    @PostMapping("/sign-out")
    public R<Void> logout() {
        userService.logout();
        return R.ok("退出成功");
    }

}
