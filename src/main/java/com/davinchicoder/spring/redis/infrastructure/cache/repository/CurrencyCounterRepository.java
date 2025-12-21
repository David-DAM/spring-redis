package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class CurrencyCounterRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void incrementCurrencyRead(String code) {
        redisTemplate.opsForValue().increment("metrics:currency:read:" + code);
    }

    public boolean isRateLimited(String clientId) {
        String key = "ratelimit:" + clientId;
        Long count = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofSeconds(60));
        return count != null && count > 10;
    }

}
