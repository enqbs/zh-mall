package com.enqbs.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtUtil {

    /*
    * 创建 token
    * */
    public static String createToken(String key, String value, Integer expire, String secret) {
        /* 获取日期偏移量，1天后的时间 */
        DateTime expireDate = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, expire);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withClaim(key, value).withExpiresAt(expireDate).sign(algorithm);
    }

    /*
    * 从 token 中获取字符串内容
    * */
    public static String getString(String token, String key) {
        return JWT.decode(token).getClaim(key).asString();
    }

    /*
    * 获取 token 过期时间
    * */
    public static Long getExpire(String token) {
        return JWT.decode(token).getClaim("exp").asLong();
    }

    /*
    * 验证 token 有效性
    * */
    public static void verifierToken(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWT.require(algorithm).build().verify(token);
    }

}
