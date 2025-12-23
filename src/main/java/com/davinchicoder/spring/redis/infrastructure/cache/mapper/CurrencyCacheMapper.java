package com.davinchicoder.spring.redis.infrastructure.cache.mapper;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.domain.CurrencyEvent;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import org.springframework.stereotype.Component;

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

    public CurrencyEvent mapToCurrencyEvent(Map<String, String> entry) {

        CurrencyEvent currencyEvent = new CurrencyEvent();
        currencyEvent.setEventId(entry.get("eventId"));
        currencyEvent.setCurrencyId(entry.get("currencyId"));
        currencyEvent.setType(entry.get("type"));
        currencyEvent.setTimestamp(Long.valueOf(entry.get("timestamp")));

        return currencyEvent;
    }

    public Map<String, String> mapCurrencyEventToHashMap(CurrencyEvent currencyEvent) {
        Map<String, String> map = new HashMap<>();
        map.put("eventId", currencyEvent.getEventId());
        map.put("currencyId", currencyEvent.getCurrencyId());
        map.put("type", currencyEvent.getType());
        map.put("timestamp", currencyEvent.getTimestamp().toString());

        return map;
    }


}
