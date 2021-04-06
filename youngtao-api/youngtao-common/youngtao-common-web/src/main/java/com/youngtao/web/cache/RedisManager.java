package com.youngtao.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public RedisManager (RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ------ string

    public <V> V get(K key) {
        return (V) redisTemplate.opsForValue().get(key);
    }

    public void set(K key, Object value) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, value);
        }
        redisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);
    }

    public void set(K key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Integer getNum(String key) {
        String num = stringRedisTemplate.opsForValue().get(key);
        return num == null ? null : Integer.valueOf(num);
    }

    public void setNum(String key, Number num) {
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.opsForValue().set(key, num.toString());
        }
        stringRedisTemplate.opsForValue().set(key, num.toString(), 30, TimeUnit.MINUTES);
    }

    public void setNum(String key, Number num, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, num.toString(), timeout, timeUnit);
    }

    public long decrement(String key) {
        return stringRedisTemplate.opsForValue().decrement(key);
    }

    public long decrement(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

    public long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    public long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    // ------ hash

    public <T> List<T> hashValues(String obj) {
        return (List<T>) redisTemplate.boundHashOps(obj).values();
    }

    public <T> T hget(String objKey, K key) {
        return (T) redisTemplate.boundHashOps(objKey).get(key);
    }

    public void hput(String objKey, K key, Object value) {
        if (redisTemplate.hasKey(objKey)) {
            redisTemplate.boundHashOps(objKey).put(key, value);
        }
        redisTemplate.boundHashOps(objKey).put(key, value);
        defaultTimeout(objKey);
    }

    public void hput(String objKey, K key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.boundHashOps(objKey).put(key, value);
        redisTemplate.boundHashOps(objKey).expire(timeout, timeUnit);
    }

    public long hincr(String objKey, K key, long delta) {
        return redisTemplate.boundHashOps("1").increment(key, delta);
    }

    public Double hincr(String objKey, K key, double delta) {
        return redisTemplate.boundHashOps("1").increment(key, delta);
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
