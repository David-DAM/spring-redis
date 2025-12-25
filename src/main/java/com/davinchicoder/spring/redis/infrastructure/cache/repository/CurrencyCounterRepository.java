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
    public static final int RATE_LIMIT_DURATION_SECONDS = 60;
    public static final int RATE_LIMIT_THRESHOLD = 10;

    private final RedisTemplate<String, String> redisTemplate;

    public void incrementCurrencyRead(String code) {
        redisTemplate.opsForValue().increment(getCurrencyMetricKey(code));
    }

    public boolean isRateLimited(String clientId) {
        String key = getRateLimitKey(clientId);
        Long count = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofSeconds(RATE_LIMIT_DURATION_SECONDS));
        return count != null && count > RATE_LIMIT_THRESHOLD;
    }

    private String getCurrencyMetricKey(String code) {
        return String.format(currencyMetricKey, code);
    }

    private String getRateLimitKey(String clientId) {
        return String.format(rateLimitKey, clientId);
    }

}
