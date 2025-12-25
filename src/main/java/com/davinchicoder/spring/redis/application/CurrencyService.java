package com.davinchicoder.spring.redis.application;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.domain.CurrencyNotFoundException;
import com.davinchicoder.spring.redis.domain.TooManyRequestException;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.CurrencyCounterRepository;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.CurrencyRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    
    private final CurrencyRedisRepository currencyRedisRepository;
    private final CurrencyCounterRepository currencyCounterRepository;

    public Currency getById(String code, String userId) {
        log.info("Getting currency by code: {}", code);
        currencyCounterRepository.incrementCurrencyRead(code);
        if (currencyCounterRepository.isRateLimited(userId)) {
            throw new TooManyRequestException();
        }
        return currencyRedisRepository.getById(code).orElseThrow(() -> new CurrencyNotFoundException(code));
    }

    public List<Currency> getAll(String userId) {
        log.info("Getting all currencies");
        if (currencyCounterRepository.isRateLimited(userId)) {
            throw new TooManyRequestException();
        }
        return currencyRedisRepository.getAll();
    }


    public void upsert(Currency currency) {
        log.info("Upserting currency: {}", currency.getCode());
        currencyRedisRepository.upsert(currency);
    }


    public void upsertAll(List<Currency> currencies) {
        log.info("Upserting {} currencies", currencies.size());
        currencyRedisRepository.upsertAll(currencies);
    }


    public void delete(String code) {
        log.info("Deleting currency: {}", code);
        currencyRedisRepository.delete(code);
    }

    public void deleteAll() {
        log.info("Deleting all currencies");
        currencyRedisRepository.deleteAll();
    }

}
