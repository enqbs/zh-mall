package com.enqbs.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setObject(String key, Object value) {
        setObject(key, value, null);
    }

    public void setObject(String key, Object value, Long timeout) {
        redisTemplate.opsForValue().set(key, value);

        if (timeout != null) {
            redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
        }
    }

    public Object getObject(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    public Boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }

}
