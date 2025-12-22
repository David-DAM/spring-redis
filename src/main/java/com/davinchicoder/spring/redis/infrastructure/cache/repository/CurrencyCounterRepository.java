package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class CurrencyCounterRepository {
    
    @Value("${app.redis.currency.keys.rate-limit}")
    private String rateLimitKey;

    @Value("${app.redis.currency.keys.metrics}")
    private String currencyMetricKey;

    private final RedisTemplate<String, String> redisTemplate;

    public void incrementCurrencyRead(String code) {
        redisTemplate.opsForValue().increment(getCurrencyMetricKey(code));
    }

    public boolean isRateLimited(String clientId) {
        String key = getRateLimitKey(clientId);
        Long count = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofSeconds(60));
        return count != null && count > 10;
    }

    private String getCurrencyMetricKey(String code) {
        return String.format(currencyMetricKey, code);
    }

    private String getRateLimitKey(String clientId) {
        return String.format(rateLimitKey, clientId);
    }

}
