package com.youngtao.web.cache;

import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/12/25
 */
@SuppressWarnings("all")
public class RedisManager<K> {
    private RedisTemplate redisTemplate;

    public RedisManager (RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ------ string

    public <V> V get(K key) {
        return (V) redisTemplate.opsForValue().get(key);
    }

    public void set(K key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }


    // ------ hash

    public <T> List<T> hashValues(String namespace) {
        return (List<T>) redisTemplate.boundHashOps(namespace).values();
    }

    public void put(String namespace, K key, Object value) {
        if (redisTemplate.hasKey(namespace)) {
            redisTemplate.boundHashOps(namespace).put(key, value);
        }
        redisTemplate.boundHashOps(namespace).put(key, value);
        defaultTimeout(namespace);
    }

    public void put(String namespace, K key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.boundHashOps(namespace).put(key, value);
        redisTemplate.boundHashOps(namespace).expire(timeout, timeUnit);
    }

    // ------ zset

    public boolean zadd(K key, Object value) {
        if (redisTemplate.hasKey(key)) {
            return redisTemplate.opsForZSet().add(key, value, 0.0);
        }
        redisTemplate.opsForZSet().add(key, value, 0.0);
        return defaultTimeout(key);
    }

    public boolean zadd(K key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForZSet().add(key, value, 0.0);
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public long zaddAll(K key, Collection<?> value) {
        Set<DefaultTypedTuple<Object>> values = value.stream().map(val -> new DefaultTypedTuple<Object>(val, 0.0)).collect(Collectors.toSet());
        if (redisTemplate.hasKey(key)) {
            return redisTemplate.opsForZSet().add(key, values);
        }
        long res = redisTemplate.opsForZSet().add(key, values);
        defaultTimeout(key);
        return res;
    }

    public boolean zaddAll(K key, Collection<?> value, long timeout, TimeUnit timeUnit) {
        Set<DefaultTypedTuple<Object>> values = value.stream().map(val -> new DefaultTypedTuple<Object>(val, 0.0)).collect(Collectors.toSet());
        redisTemplate.opsForZSet().add(key, values);
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public <T> Set<T> zvalues(K key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    public <T> Set<T> zrange(K key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public boolean defaultTimeout(Object key) {
        return redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }
}
