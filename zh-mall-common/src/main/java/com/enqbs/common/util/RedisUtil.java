package com.enqbs.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setObject(String key, Object value, Long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public void setHash(String key, Object hashKey, Object value) {
        setHash(key, hashKey, value, null);
    }

    public void setHash(String key, Object hashKey, Object value, Long timeout) {
        redisTemplate.opsForHash().put(key, hashKey, value);

        if (timeout != null) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    public Object getObject(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Map<Object, Object> getRedisMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Object getRedisMapValue(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    public void deleteEntry(String key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public Boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long executeScript(String script, String key, Object value) {
        return redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(key), value);
    }

}
