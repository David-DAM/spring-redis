package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CurrencyRankingRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void updateVolatility(String code, double volatility) {
        redisTemplate.opsForZSet()
                .incrementScore("currency-volatility", code, volatility);
    }

    public Set<String> topVolatileCurrencies() {
        return redisTemplate.opsForZSet()
                .reverseRange("currency-volatility", 0, 9);
    }

}
