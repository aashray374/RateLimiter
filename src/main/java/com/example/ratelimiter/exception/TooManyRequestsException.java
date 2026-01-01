package com.example.ratelimiter.exception;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException() {
        super("Too many requests");
    }
}
