package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CurrencyStreamRepository {

    private final RedisTemplate<String, CurrencyCacheEntity> redisTemplate;

    public void publishCurrencyEvent(Currency currency, String type) {
        redisTemplate.opsForStream().add(
                StreamRecords.newRecord()
                        .in("currency-events")
                        .ofMap(Map.of(
                                "type", type,
                                "code", currency.getCode(),
                                "rate", currency.getRatePerUsd().toString()
                        ))
        );
    }


    public List<MapRecord<String, Object, Object>> readCurrencyEvents() {
        return redisTemplate.opsForStream().read(
                Consumer.from("currency-group", "instance-1"),
                StreamReadOptions.empty().count(10),
                StreamOffset.create("currency-events", ReadOffset.lastConsumed())
        );
    }

}
