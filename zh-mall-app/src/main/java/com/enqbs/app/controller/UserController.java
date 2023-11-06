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
    public R<Map<String, Object>> login(@Valid @RequestBody LoginForm form) {
        Map<String, Object> resultMap = userService.login(form);
        return R.ok("登录成功", resultMap);
    }

    @PostMapping("/register/username")
    public R<Map<String, Object>> register(@Valid @RequestBody RegisterByUsernameForm form) {
        Map<String, Object> resultMap = userService.registerByUsername(form);
        return R.ok("注册成功", resultMap);
    }

    @GetMapping("/user/info")
    public R<Map<String, Object>> userInfo(@RequestHeader String token) throws Exception {
        String newToken = tokenService.refreshToken(token).get();
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", userInfoVO);
        resultMap.put("token", newToken);
        return R.ok(resultMap);
    }

    @PostMapping("/sign-out")
    public R<Void> logout() {
        userService.logout();
        return R.ok("退出成功");
    }

}
