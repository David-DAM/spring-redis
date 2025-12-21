package com.davinchicoder.spring.redis.application;

import com.davinchicoder.spring.redis.infrastructure.cache.entity.PricesCacheEntity;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.PricesRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricesService {

    public static final String CACHE_PRICES = "prices";
    private final PricesRedisRepository pricesRedisRepository;

    @Cacheable(value = CACHE_PRICES, key = "#symbol")
    public PricesCacheEntity getPrice(String symbol) {
        return pricesRedisRepository.getPrice(symbol);
    }

    @CacheEvict(value = CACHE_PRICES, key = "#pricesCacheEntity.symbol")
    public void savePrice(PricesCacheEntity pricesCacheEntity) {
        pricesRedisRepository.savePrice(pricesCacheEntity);
    }

    @CacheEvict(value = CACHE_PRICES, key = "#symbol")
    public void deletePrice(String symbol) {
        pricesRedisRepository.deletePrice(symbol);
    }

    @CacheEvict(value = CACHE_PRICES, allEntries = true)
    public void deleteAllPrices() {
        pricesRedisRepository.deleteAllPrices();
    }

}
