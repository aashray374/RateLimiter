package com.example.ratelimiter;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RedisTokenBucketService {
    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> tokenBucketScript;

    public RedisTokenBucketService(
            StringRedisTemplate redisTemplate,
            DefaultRedisScript<Long> tokenBucketScript) {
        this.redisTemplate = redisTemplate;
        this.tokenBucketScript = tokenBucketScript;
    }

    public boolean allowRequest(String key) {
        long now = System.currentTimeMillis() / 1000;

        Long result = redisTemplate.execute(
                tokenBucketScript,
                Collections.singletonList(key),
                "10",   // capacity
                "1",    // refill rate (1 token/sec)
                String.valueOf(now),
                "120"   // TTL
        );

        return result == 1;
    }
}
