package com.example.ratelimiter.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RedisTokenBucketExecutor {
    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> tokenBucketScript;

    public RedisTokenBucketExecutor(
            StringRedisTemplate redisTemplate,
            DefaultRedisScript<Long> tokenBucketScript) {
        this.redisTemplate = redisTemplate;
        this.tokenBucketScript = tokenBucketScript;
    }

    public boolean allowRequest(String key,String capacity,String refilRate,String ttl) {
        long now = System.currentTimeMillis() / 1000;

        Long result = redisTemplate.execute(
                tokenBucketScript,
                Collections.singletonList(key),
                capacity,
                refilRate,
                String.valueOf(now),
                ttl
        );

        return result == 1;
    }
}
