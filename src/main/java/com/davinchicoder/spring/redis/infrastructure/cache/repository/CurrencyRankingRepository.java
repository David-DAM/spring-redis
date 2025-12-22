package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CurrencyRankingRepository {

    @Value("${app.redis.currency.keys.volatility}")
    private String volatilityKey;
    private final RedisTemplate<String, String> redisTemplate;

    public void updateVolatility(String code, double volatility) {
        redisTemplate.opsForZSet()
                .incrementScore(volatilityKey, code, volatility);
    }

    public Set<String> topVolatileCurrencies() {
        return redisTemplate.opsForZSet()
                .reverseRange(volatilityKey, 0, 9);
    }

}
