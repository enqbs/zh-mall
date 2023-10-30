package com.enqbs.security.service;

import com.enqbs.security.pojo.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

public interface TokenService {

    /*
     * 创建 token
     * */
    String getToken(LoginUser loginUser);

    /*
     * 请求头获取 token
     * */
    String getToken(HttpServletRequest request);

    /*
     * 刷新 token
     * */
    Future<String> refreshToken(String token);

    /*
     * 获取 LoginUser
     * */
    LoginUser getLoginUser();

    /*
     * Token 获取 LoginUser
     * */
    LoginUser getLoginUser(String token);

    /*
     * 验证 token 有效性
     * */
    void verifierToken(String token);

}
