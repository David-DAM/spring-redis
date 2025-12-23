package com.davinchicoder.spring.redis.infrastructure.cache.mapper;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrencyCacheMapper {


    public CurrencyCacheEntity mapToCacheEntity(Currency currency) {
        CurrencyCacheEntity cache = new CurrencyCacheEntity();
        cache.setCode(currency.getCode());
        cache.setName(currency.getName());
        cache.setSymbol(currency.getSymbol());
        cache.setRatePerUsd(currency.getRatePerUsd());
        cache.setLastModified(currency.getLastModified());

        return cache;
    }

    public Currency mapToDomain(CurrencyCacheEntity cache) {

        Currency currency = new Currency();
        currency.setCode(cache.getCode());
        currency.setName(cache.getName());
        currency.setSymbol(cache.getSymbol());
        currency.setRatePerUsd(cache.getRatePerUsd());
        currency.setLastModified(cache.getLastModified());

        return currency;
    }


}
