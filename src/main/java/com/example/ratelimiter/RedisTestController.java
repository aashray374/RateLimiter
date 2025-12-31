package com.example.ratelimiter;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    private final StringRedisTemplate redisTemplate;

    public RedisTestController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/")
    public String test(){
        redisTemplate.opsForValue().set("test","ok");
        return redisTemplate.opsForValue().get("test");
    }

}
