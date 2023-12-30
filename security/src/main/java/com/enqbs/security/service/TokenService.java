package com.enqbs.security.service;

import com.enqbs.security.pojo.LoginUser;
import jakarta.servlet.http.HttpServletRequest;

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
    String refreshToken(String token);

    /*
     * 获取 LoginUser
     * */
    LoginUser getLoginUser();

    /*
     * Token 获取 LoginUser
     * */
    LoginUser getLoginUser(String token);

    /*
     * 移除 LoginUser
     * */
    void removeLoginUser();

    /*
     * 验证 token 有效性
     * */
    boolean verifierToken(String token);

}
