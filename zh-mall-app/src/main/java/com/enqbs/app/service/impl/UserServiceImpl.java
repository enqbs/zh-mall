package com.enqbs.app.service.impl;

import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.service.UserService;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.IDUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.UserAuthsMapper;
import com.enqbs.generator.dao.UserLevelMapper;
import com.enqbs.generator.dao.UserMapper;
import com.enqbs.generator.pojo.User;
import com.enqbs.generator.pojo.UserAuths;
import com.enqbs.generator.pojo.UserLevel;
import com.enqbs.security.pojo.LoginUser;
import com.enqbs.security.service.TokenService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserAuthsMapper userAuthsMapper;

    @Resource
    private UserLevelMapper userLevelMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private TokenService tokenService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> login(LoginForm form) {
        UserAuths userAuths = userAuthsMapper.selectByIdentifier(form.getUsername());

        if (ObjectUtils.isEmpty(userAuths)) {
            throw new ServiceException("用户不存在");
        }

        if (!passwordEncoder.matches(form.getPassword(), userAuths.getCredential())) {
            throw new ServiceException("密码错误");
        }
        User user = userMapper.selectByPrimaryKey(userAuths.getUserId());
        UserLevel userLevel = userLevelMapper.selectByPrimaryKey(user.getLevelId());
        LoginUser loginUser = getLoginUser(user, userAuths, userLevel);
        cacheLoginUser(loginUser);
        String token = tokenService.getToken(loginUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> registerByUsername(RegisterByUsernameForm form) {
        int count = userAuthsMapper.countIdByIdentifier(form.getUsername());

        if (count > 0) {
            throw new ServiceException("用户已存在");
        }
        User user = buildUser();
        int insertUserRow = userMapper.insertSelective(user);

        if (insertUserRow <= 0) {
            throw new ServiceException("用户信息保存失败");
        }
        UserAuths userAuths = buildUserAuths(user.getId(), Constants.LOGIN_TYPE_USERNAME,
                form.getUsername(), passwordEncoder.encode(form.getPassword()));
        int insertUserAuthsRow = userAuthsMapper.insertSelective(userAuths);

        if (insertUserAuthsRow <= 0) {
            throw new ServiceException("用户账号密码保存失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        return map;
    }

    @Override
    public UserInfoVO getUserInfoVO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(authentication.getPrincipal(), userInfoVO);
        return userInfoVO;
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        removeCacheLoginUser(loginUser);
        SecurityContextHolder.clearContext();
    }

    private LoginUser getLoginUser(User user, UserAuths userAuths, UserLevel userLevel) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserToken(IDUtil.getUUID());
        loginUser.setUserType(Constants.USER_TOKEN);
        loginUser.setUserId(user.getId());
        loginUser.setUsername(userAuths.getIdentifier());
        loginUser.setPassword(userAuths.getCredential());
        loginUser.setUid(user.getUid());
        loginUser.setNickName(user.getNickName());
        loginUser.setPhoto(user.getPhoto());
        loginUser.setGender(user.getGender());
        loginUser.setExperience(user.getExperience());

        if (ObjectUtils.isNotEmpty(userLevel)) {
            loginUser.setLevel(userLevel.getLevel());
            loginUser.setLevelTitle(userLevel.getTitle());
            loginUser.setLevelExperience(userLevel.getExperience());
            loginUser.setDiscount(userLevel.getDiscount());
        }
        return loginUser;
    }

    private void cacheLoginUser(LoginUser loginUser) {
        String redisKey = String.format(Constants.USER_REDIS_KEY, loginUser.getUserToken());
        long cacheTimeout = 3600 * 24 * 10 * 1000L;     // 用户信息 redis 缓存10天(免登录)
        redisUtil.setObject(redisKey, loginUser, cacheTimeout);
    }

    private void removeCacheLoginUser(LoginUser loginUser) {
        String redisKey = String.format(Constants.USER_REDIS_KEY, loginUser.getUserToken());
        redisUtil.deleteObject(redisKey);
    }

    private User buildUser() {
        long uid = IDUtil.getId();
        User user = new User();
        user.setUid(uid);
        user.setNickName("用户:" + uid);
        user.setPhoto("https://zh-product.oss-cn-shenzhen.aliyuncs.com/user-photo/photo.png");
        return user;
    }

    private UserAuths buildUserAuths(Integer userId, String identityType, String identifier, String credential) {
        UserAuths userAuths = new UserAuths();
        userAuths.setUserId(userId);                    // 用户ID
        userAuths.setIdentityType(identityType);        // 登录/注册方式
        userAuths.setIdentifier(identifier);            // 登录标识符
        userAuths.setCredential(credential);            // 登录凭证
        return userAuths;
    }

}
