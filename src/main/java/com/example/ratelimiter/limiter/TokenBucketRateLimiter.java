package com.example.ratelimiter.limiter;

import com.example.ratelimiter.redis.RedisTokenBucketExecutor;
import org.springframework.stereotype.Service;

@Service
public class TokenBucketRateLimiter implements RateLimiter{

    private final RedisTokenBucketExecutor redisTokenBucketExecutor;

    public TokenBucketRateLimiter(RedisTokenBucketExecutor redisTokenBucketExecutor) {
        this.redisTokenBucketExecutor = redisTokenBucketExecutor;
    }

    @Override
    public boolean allow(String key) {
        return redisTokenBucketExecutor.allowRequest(
                key,
                "10",
                "1",
                "120"
        );
    }
}
