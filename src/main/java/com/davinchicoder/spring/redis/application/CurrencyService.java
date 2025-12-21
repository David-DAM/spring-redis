package com.davinchicoder.spring.redis.application;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.CurrencyRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    public static final String CACHE_CURRENCIES = "currencies";
    private final CurrencyRedisRepository currencyRedisRepository;

    @Cacheable(value = CACHE_CURRENCIES, key = "#code")
    public Optional<Currency> getCurrency(String code) {
        return currencyRedisRepository.getCurrency(code);
    }

    @Cacheable(value = CACHE_CURRENCIES)
    public List<Currency> getAllCurrencies() {
        return currencyRedisRepository.getAllCurrencies();
    }

    @CacheEvict(value = CACHE_CURRENCIES, key = "#currency.code")
    public void saveCurrency(Currency currency) {
        currencyRedisRepository.saveCurrency(currency);
    }

    @CacheEvict(value = CACHE_CURRENCIES, key = "#code")
    public void deleteCurrency(String code) {
        currencyRedisRepository.deletePrice(code);
    }

    @CacheEvict(value = CACHE_CURRENCIES, allEntries = true)
    public void deleteAllCurrencies() {
        currencyRedisRepository.deleteAllPrices();
    }

}
