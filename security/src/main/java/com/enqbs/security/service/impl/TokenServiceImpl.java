package com.enqbs.security.service.impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.JwtUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.security.config.JwtProperties;
import com.enqbs.security.pojo.LoginUser;
import com.enqbs.security.service.TokenService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JwtProperties jwtProperties;

    @Override
    public String getToken(LoginUser loginUser) {
        return JwtUtil.createToken(loginUser.getUserType(), loginUser.getUserToken(), jwtProperties.getExpire(), jwtProperties.getSecret());
    }

    @Override
    public String getToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    @Override
    public String refreshToken(String token) {
        long expireTime = JwtUtil.getExpire(token);                 // JWT 过期时间
        long currentTime = System.currentTimeMillis() / 1000;       // 当前时间
        return expireTime - currentTime <= 3600L ? getNewToken(token) : token;        // 过期时间小于1小时,返回新 token
    }

    @Override
    public LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUser) authentication.getPrincipal();
    }

    @Override
    public LoginUser getLoginUser(String token) {
        String userToken = getUserToken(token);
        String redisKey = StringUtils.isNotEmpty(userToken) ?
                String.format(Constants.USER_REDIS_KEY, userToken) :
                String.format(Constants.SYS_USER_REDIS_KEY, getSysUserToken(token));
        return GsonUtil.json2Obj(redisUtil.getString(redisKey), LoginUser.class);
    }

    @Override
    public void removeLoginUser() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public boolean verifierToken(String token) {
        try {
            JwtUtil.verifierToken(token, jwtProperties.getSecret());
            return true;
        } catch (Exception e) {
            if (e instanceof TokenExpiredException) {
                String userToken = getUserToken(token);
                String redisKey = StringUtils.isNotEmpty(userToken) ?
                        String.format(Constants.USER_REDIS_KEY, userToken) :
                        String.format(Constants.SYS_USER_REDIS_KEY, getSysUserToken(token));

                if (!redisUtil.exist(redisKey)) {
                    throw new ServiceException("用户信息已过期,请重新登录");
                }

                return true;
            }

            log.warn("无效Token:'{}'.", token);
            return false;
        }
    }

    private String getNewToken(String token) {
        String userToken = getUserToken(token);
        return StringUtils.isNotEmpty(userToken) ?
                JwtUtil.createToken(Constants.USER_TOKEN, userToken, jwtProperties.getExpire(), jwtProperties.getSecret()) :
                JwtUtil.createToken(Constants.SYS_USER_TOKEN, getSysUserToken(token), jwtProperties.getExpire(), jwtProperties.getSecret());
    }

    private String getUserToken(String token) {
        return JwtUtil.getString(token, Constants.USER_TOKEN);
    }

    private String getSysUserToken(String token) {
        return JwtUtil.getString(token, Constants.SYS_USER_TOKEN);
    }

}
