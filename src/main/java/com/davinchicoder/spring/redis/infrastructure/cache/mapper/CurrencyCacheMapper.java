package com.davinchicoder.spring.redis.infrastructure.cache.mapper;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    public Currency mapToDomain(Map<Object, Object> entry) {

        Currency currency = new Currency();
        currency.setCode(entry.get("code").toString());
        currency.setName(entry.get("name").toString());
        currency.setSymbol(entry.get("symbol").toString());
        currency.setRatePerUsd(Double.valueOf(entry.get("rate_per_usd").toString()));
        currency.setLastModified(LocalDateTime.parse(entry.get("last_modified").toString()));

        return currency;
    }

    public Map<String, String> mapToHashMap(Currency currency, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("code", currency.getCode());
        map.put("name", currency.getName());
        map.put("symbol", currency.getSymbol());
        map.put("ratePerUsd", currency.getRatePerUsd().toString());
        map.put("lastModified", currency.getLastModified().toString());

        return map;
    }


}
