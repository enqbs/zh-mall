package com.enqbs.common.util;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setString(String key, String value, Long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public void setHash(String key, String hashKey, String value) {
        setHash(key, hashKey, value, null);
    }

    public void setHash(String key, String hashKey, String value, Long timeout) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);

        if (timeout != null) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Map<String, String> getRedisMap(String key) {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        return hash.entries(key);
    }

    public String getRedisMapValue(String key, String hashKey) {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    public void deleteKey(String key) {
        stringRedisTemplate.delete(key);
    }

    public void deleteRedisMapEntry(String key, String hashKey) {
        stringRedisTemplate.opsForHash().delete(key, hashKey);
    }

    public Boolean isExist(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public Long executeScript(String script, String key, String value) {
        return stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(key), value);
    }

}
