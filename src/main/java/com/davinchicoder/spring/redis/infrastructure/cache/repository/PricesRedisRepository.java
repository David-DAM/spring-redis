package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.infrastructure.cache.entity.PricesCacheEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PricesRedisRepository {

    private final PricesQueryRedisRepository pricesQueryRedisRepository;
    private final RedisTemplate<String, PricesCacheEntity> redisTemplate;

    public PricesCacheEntity getPrice(String symbol) {
        return pricesQueryRedisRepository.findById(symbol).orElse(null);
    }

    public void savePrice(PricesCacheEntity pricesCacheEntity) {
        pricesQueryRedisRepository.save(pricesCacheEntity);
    }

    public void deletePrice(String symbol) {
        pricesQueryRedisRepository.deleteById(symbol);
    }

    public void deleteAllPrices() {
        pricesQueryRedisRepository.deleteAll();
    }

}
