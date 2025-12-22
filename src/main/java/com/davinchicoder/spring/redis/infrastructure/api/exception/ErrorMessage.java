package com.davinchicoder.spring.redis.infrastructure.api.exception;

public record ErrorMessage(
        String message,
        String path,
        String timestamp
) {
}
