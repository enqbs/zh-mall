package com.enqbs.security.service.impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.JwtUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.security.config.JwtPramConfig;
import com.enqbs.security.pojo.LoginUser;
import com.enqbs.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JwtPramConfig jwtPramConfig;

    @Override
    public String getToken(LoginUser loginUser) {
        return JwtUtil.createToken(loginUser.getUserType(), loginUser.getUserToken(),
                jwtPramConfig.getExpire(), jwtPramConfig.getSecret());
    }

    @Override
    public String getToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public Future<String> refreshToken(String token) {
        String newToken;

        try {
            JwtUtil.verifierToken(token, jwtPramConfig.getSecret());
            long expireTime = JwtUtil.getExpire(token);                 // JWT 过期时间
            long currentTime = System.currentTimeMillis() / 1000;       // 当前时间
            long oneHour = 3600L;                                       // 一小时 3600 秒
            /* 如果 jwt 过期时间小于1小时,返回新 token */
            if (expireTime - currentTime <= oneHour) {
                newToken = getNewToken(token);
            } else {
                newToken = token;
            }
        } catch (TokenExpiredException e) {
            newToken = getNewToken(token);
        }

        return new AsyncResult<>(newToken);
    }

    @Override
    public LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUser) authentication.getPrincipal();
    }

    @Override
    public LoginUser getLoginUser(String token) {
        LoginUser loginUser;
        String redisKey;
        String userToken;
        userToken = getUserToken(token);

        if (StringUtils.isNotEmpty(userToken)) {
            redisKey = String.format(Constants.USER_REDIS_KEY, userToken);
            loginUser = (LoginUser) redisUtil.getObject(redisKey);
        } else {
            userToken = getSysUserToken(token);
            redisKey = String.format(Constants.SYS_USER_REDIS_KEY, userToken);
            loginUser = (LoginUser) redisUtil.getObject(redisKey);
        }

        return loginUser;
    }

    @Override
    public void verifierToken(String token) {
        try {
            JwtUtil.verifierToken(token, jwtPramConfig.getSecret());
        } catch (Exception e) {
            if (e instanceof TokenExpiredException) {
                String redisKey;
                String userToken;
                userToken = getUserToken(token);

                if (StringUtils.isNotEmpty(userToken)) {
                    redisKey = String.format(Constants.USER_REDIS_KEY, userToken);
                } else {
                    userToken = getSysUserToken(token);
                    redisKey = String.format(Constants.SYS_USER_REDIS_KEY, userToken);
                }

                if (!redisUtil.isExist(redisKey)) {
                    throw new ServiceException("用户信息已过期,请重新登录");
                }
            } else {
                log.error("无效token:{}", token);
                throw new ServiceException("无效的token");
            }
        }
    }

    private String getNewToken(String token) {
        String newToken;
        String userToken;
        userToken = getUserToken(token);

        if (StringUtils.isNotEmpty(userToken)) {
            newToken = JwtUtil.createToken(Constants.USER_TOKEN, userToken,
                    jwtPramConfig.getExpire(), jwtPramConfig.getSecret());
        } else {
            userToken = getSysUserToken(token);
            newToken = JwtUtil.createToken(Constants.SYS_USER_TOKEN, userToken,
                    jwtPramConfig.getExpire(), jwtPramConfig.getSecret());
        }

        return newToken;
    }

    private String getUserToken(String token) {
        return JwtUtil.getString(token, Constants.USER_TOKEN);
    }

    private String getSysUserToken(String token) {
        return JwtUtil.getString(token, Constants.SYS_USER_TOKEN);
    }

}
