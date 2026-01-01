package com.example.ratelimiter.redis;


import org.springframework.stereotype.Component;

@Component
public class RedisKeyBuilder {

    public String ipKey(String ip){
        return "rate:ip:"+ip;
    }
}
