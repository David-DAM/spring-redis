package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import com.davinchicoder.spring.redis.infrastructure.cache.mapper.CurrencyCacheMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class CurrencyRedisRepository {
    public static final String CACHE_CURRENCIES = "currencies";
    private final CurrencyQueryRedisRepository currencyQueryRedisRepository;
    private final CurrencyCacheMapper mapper;

    @Cacheable(value = CACHE_CURRENCIES, key = "#code")
    public Optional<Currency> getById(String code) {
        return currencyQueryRedisRepository.findById(code).map(mapper::mapToDomain);
    }

    @Cacheable(value = CACHE_CURRENCIES)
    public List<Currency> getAll() {
        return StreamSupport.stream(currencyQueryRedisRepository.findAll().spliterator(), false)
                .map(mapper::mapToDomain)
                .toList();
    }

    @CacheEvict(value = CACHE_CURRENCIES, key = "#currency.code")
    public void upsert(Currency currency) {
        currencyQueryRedisRepository.save(mapper.mapToCacheEntity(currency));
    }

    @CacheEvict(value = CACHE_CURRENCIES, allEntries = true)
    public void upsertAll(List<Currency> currencies) {
        List<CurrencyCacheEntity> cacheEntities = currencies.stream().map(mapper::mapToCacheEntity).toList();
        currencyQueryRedisRepository.saveAll(cacheEntities);
    }

    @CacheEvict(value = CACHE_CURRENCIES, key = "#code")
    public void delete(String code) {
        currencyQueryRedisRepository.deleteById(code);
    }

    @CacheEvict(value = CACHE_CURRENCIES, allEntries = true)
    public void deleteAll() {
        currencyQueryRedisRepository.deleteAll();
    }


}
