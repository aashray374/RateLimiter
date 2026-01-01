package com.example.ratelimiter;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    @GetMapping("/")
    public String test(){
        return "running";
    }

}
