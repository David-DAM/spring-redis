package com.davinchicoder.spring.redis.application;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.domain.CurrencyNotFoundException;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.CurrencyCounterRepository;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.CurrencyRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    public static final String CACHE_CURRENCIES = "currencies";
    private final CurrencyRedisRepository currencyRedisRepository;
    private final CurrencyCounterRepository currencyCounterRepository;

    @Cacheable(value = CACHE_CURRENCIES, key = "#code")
    public Currency getById(String code) {
        log.info("Getting currency by code: {}", code);
        currencyCounterRepository.incrementCurrencyRead(code);
        return currencyRedisRepository.getById(code).orElseThrow(() -> new CurrencyNotFoundException(code));
    }

    @Cacheable(value = CACHE_CURRENCIES)
    public List<Currency> getAll() {
        log.info("Getting all currencies");
        return currencyRedisRepository.getAll();
    }

    @CacheEvict(value = CACHE_CURRENCIES, key = "#currency.code")
    public void upsert(Currency currency) {
        log.info("Upserting currency: {}", currency.getCode());
        currencyRedisRepository.upsert(currency);
    }

    @CacheEvict(value = CACHE_CURRENCIES, allEntries = true)
    public void upsertAll(List<Currency> currencies) {
        log.info("Upserting {} currencies", currencies.size());
        currencyRedisRepository.upsertAll(currencies);
    }

    @CacheEvict(value = CACHE_CURRENCIES, key = "#code")
    public void delete(String code) {
        log.info("Deleting currency: {}", code);
        currencyRedisRepository.delete(code);
    }

    @CacheEvict(value = CACHE_CURRENCIES, allEntries = true)
    public void deleteAll() {
        log.info("Deleting all currencies");
        currencyRedisRepository.deleteAll();
    }

}
