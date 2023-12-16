package com.enqbs.app.service.user;

import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.enqbs.app.config.AliAuthConfig;
import com.enqbs.app.convert.UserConvert;
import com.enqbs.app.enums.LoginTypeEnum;
import com.enqbs.app.form.ChangeNicknameForm;
import com.enqbs.app.form.ChangePasswordForm;
import com.enqbs.app.form.ChangePhotoForm;
import com.enqbs.app.form.LoginForm;
import com.enqbs.app.form.RegisterByUsernameForm;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.IDUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.UserMapper;
import com.enqbs.generator.pojo.User;
import com.enqbs.generator.pojo.UserAuths;
import com.enqbs.generator.pojo.UserLevel;
import com.enqbs.security.pojo.LoginUser;
import com.enqbs.security.service.TokenService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private AliAuthConfig aliAuthConfig;
    @Resource
    private UserAuthsService userAuthsService;
    @Resource
    private UserLevelService userLevelService;
    @Resource
    private TokenService tokenService;
    @Resource
    private UserConvert userConvert;
    @Resource
    private ThreadPoolTaskExecutor executor;

    @Override
    public String login(LoginForm form) {
        UserAuths userAuths = userAuthsService.getUserAuths(null, form.getUsername(), null);

        if (ObjectUtils.isEmpty(userAuths)) {
            throw new ServiceException("用户不存在");
        }

        if (!passwordEncoder.matches(form.getPassword(), userAuths.getCredential())) {
            throw new ServiceException("密码错误");
        }

        LoginUser loginUser = userAuthsGetLoginUser(userAuths);
        executor.execute(() -> cacheLoginUser(loginUser));
        return tokenService.getToken(loginUser);
    }

    @Override
    public String loginByAlipayPageAuth(String code) {
        AlipaySystemOauthTokenResponse oauthToken = oauthToken(code);
        AlipayUserInfoShareResponse userInfoShare = userInfoShare(oauthToken.getAccessToken());
        String credential = userInfoShare.getUserId();

        if (StringUtils.isEmpty(credential)) {
            throw new ServiceException("临时授权失败,请重试");
        }

        UserAuths userAuths = userAuthsService.getUserAuths(null, LoginTypeEnum.ALIPAY_PC.getIdentifier(), credential);
        UserService userServiceProxy = (UserService) AopContext.currentProxy();
        LoginUser loginUser = ObjectUtils.isEmpty(userAuths) ?
                userServiceProxy.insert(credential, userInfoShare.getNickName(), userInfoShare.getAvatar()) :
                userAuthsGetLoginUser(userAuths);
        executor.execute(() -> cacheLoginUser(loginUser));
        return tokenService.getToken(loginUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer registerByUsername(RegisterByUsernameForm form) {
        Integer exist = userAuthsService.exist(null, form.getUsername(), null);

        if (ObjectUtils.isNotEmpty(exist)) {
            throw new ServiceException("用户已存在");
        }

        int row;
        User user = buildUser();
        row = userMapper.insertSelective(user);

        if (row <= 0) {
            throw new ServiceException("用户信息保存失败");
        }

        UserAuths userAuths = buildUserAuths(user.getId(), LoginTypeEnum.USERNAME.getIdentityType(),
                form.getUsername(), passwordEncoder.encode(form.getPassword()));
        row = userAuthsService.insert(userAuths);

        if (row <= 0) {
            throw new ServiceException("用户账号密码保存失败");
        }

        return user.getId();
    }

    @Override
    public UserInfoVO getUserInfoVO() {
        LoginUser loginUser = tokenService.getLoginUser();
        return userConvert.loginUser2UserInfoVO(loginUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginUser insert(String credential, String nickName, String photo) {
        User user = new User();
        user.setUid(IDUtil.getId());
        user.setNickName(nickName);
        user.setPhoto(photo);
        int row = userMapper.insertSelective(user);

        if (row <= 0) {
            throw new ServiceException("用户信息保存失败");
        }

        UserAuths userAuths = buildUserAuths(user.getId(), LoginTypeEnum.ALIPAY_PC.getIdentityType(),
                LoginTypeEnum.ALIPAY_PC.getIdentifier(), credential);
        row = userAuthsService.insert(userAuths);

        if (row <= 0) {
            throw new ServiceException("用户账号密码保存失败");
        }

        UserLevel userLevel = userLevelService.getUserLevel(user.getLevelId());
        return getLoginUser(user, userAuths, userLevel);
    }

    @Override
    public void changePassword(ChangePasswordForm form) {
        LoginUser loginUser = tokenService.getLoginUser();

        if (!passwordEncoder.matches(form.getOldPassword(), loginUser.getPassword())) {
            throw new ServiceException("密码错误");
        }

        if (!form.getNewPassword().equals(form.getNewPasswordAgain())) {
            throw new ServiceException("两次输入的密码不一样");
        }

        UserAuths userAuths = userAuthsService.getUserAuths(loginUser.getUserId(), null, null);
        userAuths.setCredential(passwordEncoder.encode(form.getNewPassword()));
        int row = userAuthsService.update(userAuths);

        if (row <= 0) {
            throw new ServiceException("修改密码失败");
        }

        logout();
    }

    @Override
    public void changeNickname(ChangeNicknameForm form) {
        LoginUser loginUser = tokenService.getLoginUser();
        User user = new User();
        user.setId(loginUser.getUserId());
        user.setNickName(form.getNickName());
        int row = userMapper.updateByPrimaryKeySelective(user);

        if (row <= 0) {
            throw new ServiceException("修改昵称失败");
        }

        executor.execute(() -> {
                    loginUser.setNickName(form.getNickName());
                    removeCacheLoginUser(loginUser);
                    cacheLoginUser(loginUser);
                }
        );
    }

    @Override
    public void changePhoto(ChangePhotoForm form) {
        LoginUser loginUser = tokenService.getLoginUser();
        User user = new User();
        user.setId(loginUser.getUserId());
        user.setPhoto(form.getPhotoURL());
        int row = userMapper.updateByPrimaryKeySelective(user);

        if (row <= 0) {
            throw new ServiceException("修改头像失败");
        }

        executor.execute(() -> {
                    loginUser.setPhoto(form.getPhotoURL());
                    removeCacheLoginUser(loginUser);
                    cacheLoginUser(loginUser);
                }
        );
    }

    @Override
    public void logout() {
        LoginUser loginUser = tokenService.getLoginUser();
        executor.execute(() -> removeCacheLoginUser(loginUser));
        tokenService.removeLoginUser();
    }

    private LoginUser userAuthsGetLoginUser(UserAuths userAuths) {
        User user = userMapper.selectByPrimaryKey(userAuths.getUserId());
        UserLevel userLevel = userLevelService.getUserLevel(user.getLevelId());
        return getLoginUser(user, userAuths, userLevel);
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

    private User buildUser() {
        long uid = IDUtil.getId();
        User user = new User();
        user.setUid(uid);
        user.setNickName("用户:" + uid);
        user.setPhoto("用户默认头像URL");
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

    private AlipaySystemOauthTokenResponse oauthToken(String code) {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");

        try {
            return aliAuthConfig.aliAuthClient().execute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private AlipayUserInfoShareResponse userInfoShare(String accessToken) {
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();

        try {
            return aliAuthConfig.aliAuthClient().execute(request, accessToken);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void cacheLoginUser(LoginUser loginUser) {
        String redisKey = String.format(Constants.USER_REDIS_KEY, loginUser.getUserToken());
        long cacheTimeout = 3600 * 24 * 10 * 1000L;     // 用户信息 redis 缓存10天(免登录)
        redisUtil.setString(redisKey, GsonUtil.obj2Json(loginUser), cacheTimeout);
    }

    private void removeCacheLoginUser(LoginUser loginUser) {
        String redisKey = String.format(Constants.USER_REDIS_KEY, loginUser.getUserToken());
        redisUtil.deleteKey(redisKey);
    }

}
