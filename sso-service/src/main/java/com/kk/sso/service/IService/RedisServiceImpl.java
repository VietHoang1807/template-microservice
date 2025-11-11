package com.kk.sso.service.IService;

import java.util.concurrent.TimeUnit;

public interface RedisServiceImpl {
    void set(String key, Object value);
    void set(String key, Object value, Long time, TimeUnit unit);
    Object get(String key);
    void clean();
    void clear(String key);
}
