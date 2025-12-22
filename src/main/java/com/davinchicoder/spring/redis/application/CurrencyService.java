package com.davinchicoder.spring.redis.application;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.CurrencyRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    public static final String CACHE_CURRENCIES = "currencies";
    private final CurrencyRedisRepository currencyRedisRepository;

    @Cacheable(value = CACHE_CURRENCIES, key = "#code")
    public Currency getById(String code) {
        return currencyRedisRepository.getById(code).orElseThrow(RuntimeException::new);
    }

    @Cacheable(value = CACHE_CURRENCIES)
    public List<Currency> getAll() {
        return currencyRedisRepository.getAll();
    }

    @CacheEvict(value = CACHE_CURRENCIES, key = "#currency.code")
    public void upsert(Currency currency) {
        currencyRedisRepository.upsert(currency);
    }

    @CacheEvict(value = CACHE_CURRENCIES, allEntries = true)
    public void upsertAll(List<Currency> currencies) {
        currencyRedisRepository.upsertAll(currencies);
    }

    @CacheEvict(value = CACHE_CURRENCIES, key = "#code")
    public void delete(String code) {
        currencyRedisRepository.delete(code);
    }

    @CacheEvict(value = CACHE_CURRENCIES, allEntries = true)
    public void deleteAll() {
        currencyRedisRepository.deleteAll();
    }

}
