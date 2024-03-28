package com.enqbs.security.service;

import com.enqbs.security.pojo.LoginUser;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {

    /**
     * 创建 token
     *
     * @param loginUser 登录用户信息
     * @return token
     */
    String getToken(LoginUser loginUser);

    /**
     * 请求头获取 token
     *
     * @param request request
     * @return token
     */
    String getToken(HttpServletRequest request);

    /**
     * 刷新 token
     *
     * @param token 旧 token
     * @return 新 token
     */
    String refreshToken(String token);

    /**
     * 获取 LoginUser
     *
     * @return 登录用户信息
     */
    LoginUser getLoginUser();

    /**
     * Token 获取 LoginUser
     *
     * @param token token
     * @return 登录用户信息
     */
    LoginUser getLoginUser(String token);

    /**
     * 移除登录用户信息
     */
    void removeLoginUser();

    /**
     * 验证 token 有效性
     *
     * @param token token
     * @return token 是否有效
     */
    boolean verifierToken(String token);

}
