package com.youngtao.web.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 双重缓存
 * @author ankoye@qq.com
 * @date 2020/12/26
 */
@SuppressWarnings("all")
public class DCacheManager<K> {
    private Cache<K, Object> cache;
    private RedisTemplate redisTemplate;
    /**
     * 单位：分，默认30
     */
    private long redisTimeout;

    public DCacheManager(RedisTemplate redisTemplate) {
        cache = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        this.redisTimeout = 30;
    }

    public DCacheManager(RedisTemplate redisTemplate, long redisTimeout) {
        this(redisTemplate);
        this.redisTimeout = redisTimeout;
    }

    public DCacheManager(Cache<K, Object> cache, RedisTemplate redisTemplate) {
        this.cache = cache;
        this.redisTemplate = redisTemplate;
    }

    public DCacheManager(Cache<K, Object> cache, RedisTemplate redisTemplate, long redisTimeout) {
        this.cache = cache;
        this.redisTemplate = redisTemplate;
        this.redisTimeout = redisTimeout;
    }

    public <V> V get(K key) {
        return get(key, null);
    }

    public <V> V get(K key, Function<K, ? extends V> func) {
        return get(key, func, false);
    }

    public <V> V get(K key, Function<K, ? extends V> func, boolean cacheNull) {
        if (redisTemplate == null) {
            throw new NullPointerException("redisTemplate may be not init");
        }
        return (V) cache.get(key, k -> {
            V value = (V) redisTemplate.opsForValue().get(key);
            if (value == null && func != null) {
                value = func.apply(null);
                if (value != null) {
                    redisTemplate.opsForValue().set(key, value, redisTimeout, TimeUnit.MINUTES);
                } else if (cacheNull) {
                    redisTemplate.opsForValue().set(key, value, 1, TimeUnit.MINUTES);
                }
            }
            return value;
        });
    }
}
