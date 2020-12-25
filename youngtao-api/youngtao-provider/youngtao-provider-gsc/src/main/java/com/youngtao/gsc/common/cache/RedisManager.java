package com.youngtao.gsc.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/12/25
 */
@Component
@SuppressWarnings("all")
public class RedisManager {
    @Autowired
    private RedisTemplate redisTemplate;

    // ------ string

    public void set(Object key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }


    // ------ hash

    public <T> List<T> hashValues(Object namespace) {
        return (List<T>) redisTemplate.boundHashOps(namespace).values();
    }

    public void put(Object namespace, Object key, Object value) {
        redisTemplate.boundHashOps(namespace).put(key, value);
    }

    public void put(Object namespace, Object key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.boundHashOps(namespace).put(key, value);
        redisTemplate.boundHashOps(namespace).expire(timeout, timeUnit);
    }

    // ------ zset

    public boolean zaddAll(Object key, Set<?> value, long timeout, TimeUnit timeUnit) {
        Set<DefaultTypedTuple<Object>> values = value.stream().map(val -> new DefaultTypedTuple<Object>(val, 0.0)).collect(Collectors.toSet());
        redisTemplate.opsForZSet().add(key, values);
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public boolean zadd(Object key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForZSet().add(key, value, 0.0);
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public <T> Set<T> zvaluse(Object key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    public <T> Set<T> zrange(Object key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

}
