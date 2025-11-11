package com.kk.sso.service;

import com.kk.sso.service.IService.RedisServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService implements RedisServiceImpl {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, Long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, time, unit);
    }

    @Override
    public Object get(String key) {
        if (!isKeyExpired(key))
            return null;
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void clean() {
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("*")));
    }

    @Override
    public void clear(String key) {
        redisTemplate.delete(key);
    }

    private boolean isKeyExpired(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
