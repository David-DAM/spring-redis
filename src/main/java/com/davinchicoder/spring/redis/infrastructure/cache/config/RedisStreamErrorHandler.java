package com.davinchicoder.spring.redis.infrastructure.cache.config;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

@Slf4j
public class RedisStreamErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {

        Throwable cause = t.getCause();

        if (cause instanceof RedisException &&
                cause.getMessage() != null &&
                cause.getMessage().contains("Connection closed")) {

            log.debug("Redis connection closed during shutdown");
            return;
        }
        
        log.error("Unexpected Redis Stream error", t);
    }
}

