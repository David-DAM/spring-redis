package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CurrencyRankingRepository {

    public static final String CURRENCY_VOLATILITY_KEY = "currency-volatility";
    private final RedisTemplate<String, String> redisTemplate;

    public void updateVolatility(String code, double volatility) {
        redisTemplate.opsForZSet()
                .incrementScore(CURRENCY_VOLATILITY_KEY, code, volatility);
    }

    public Set<String> topVolatileCurrencies() {
        return redisTemplate.opsForZSet()
                .reverseRange(CURRENCY_VOLATILITY_KEY, 0, 9);
    }

}
