package com.davinchicoder.spring.redis.domain;

public class TooManyRequestException extends RuntimeException {
    public TooManyRequestException() {
        super("You have exceeded the maximum number of requests allowed.");
    }
}
