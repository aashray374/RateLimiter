package com.example.ratelimiter.filter;

import com.example.ratelimiter.limiter.RateLimiter;
import com.example.ratelimiter.redis.RedisKeyBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {

    private final RedisKeyBuilder redisKeyBuilder;
    private final RateLimiter rateLimiter;

    public RateLimiterFilter(RedisKeyBuilder redisKeyBuilder, RateLimiter rateLimiter) {
        this.redisKeyBuilder = redisKeyBuilder;
        this.rateLimiter = rateLimiter;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        String key = redisKeyBuilder.ipKey(ip);

        if (!rateLimiter.allow(key)) {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

