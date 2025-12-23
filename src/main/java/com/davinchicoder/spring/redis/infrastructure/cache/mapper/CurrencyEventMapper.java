package com.davinchicoder.spring.redis.infrastructure.cache.mapper;

import com.davinchicoder.spring.redis.domain.CurrencyEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyEventMapper {

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
