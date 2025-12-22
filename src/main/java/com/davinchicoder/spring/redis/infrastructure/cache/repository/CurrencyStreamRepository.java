package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import com.davinchicoder.spring.redis.infrastructure.cache.mapper.CurrencyCacheMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CurrencyStreamRepository {

    public static final String CURRENCY_EVENT_STREAM = "currency-events";
    public static final String CONSUMER_GROUP_NAME = "currency-group";
    public static final String CONSUMER_INSTANCE_NAME = "instance-1";
    private final RedisTemplate<String, CurrencyCacheEntity> redisTemplate;
    private final CurrencyCacheMapper mapper;

    public void publishCurrencyEvent(Currency currency, String type) {
        redisTemplate.opsForStream().add(
                StreamRecords.newRecord()
                        .in(CURRENCY_EVENT_STREAM)
                        .ofMap(mapper.mapToHashMap(currency, type))
        );
    }


    public List<Currency> readCurrencyEvents() {
        return redisTemplate.opsForStream().read(
                        Consumer.from(CONSUMER_GROUP_NAME, CONSUMER_INSTANCE_NAME),
                        StreamReadOptions.empty().count(10),
                        StreamOffset.create(CURRENCY_EVENT_STREAM, ReadOffset.lastConsumed())
                ).stream()
                .map(MapRecord::getValue)
                .map(mapper::mapToDomain)
                .toList();
    }

}
