package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import com.davinchicoder.spring.redis.infrastructure.cache.mapper.CurrencyCacheMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CurrencyStreamRepository {

    @Value("${app.redis.currency.keys.events}")
    private String currencyEventKey;

    @Value("${app.redis.currency.keys.group-name}")
    private String currencyGroupName;

    @Value("${app.redis.currency.keys.instance-name}")
    private String currencyInstanceName;
    private final RedisTemplate<String, CurrencyCacheEntity> redisTemplate;
    private final CurrencyCacheMapper mapper;

    public void publishCurrencyEvent(Currency currency, String type) {
        redisTemplate.opsForStream().add(
                StreamRecords.newRecord()
                        .in(currencyEventKey)
                        .ofMap(mapper.mapToHashMap(currency, type))
        );
    }


    public List<Currency> readCurrencyEvents() {
        return redisTemplate.opsForStream().read(
                        Consumer.from(currencyGroupName, currencyInstanceName),
                        StreamReadOptions.empty().count(10),
                        StreamOffset.create(currencyEventKey, ReadOffset.lastConsumed())
                ).stream()
                .map(MapRecord::getValue)
                .map(mapper::mapToDomain)
                .toList();
    }

    public void consumeEvents() {
        List<MapRecord<String, Object, Object>> messages =
                redisTemplate.opsForStream().read(
                        Consumer.from(currencyGroupName, currencyInstanceName),
                        StreamReadOptions.empty().count(10).block(Duration.ofSeconds(5)),
                        StreamOffset.create(
                                currencyEventKey,
                                ReadOffset.lastConsumed()
                        )
                );

        if (messages == null) return;

        messages.forEach(msg -> {
            log.info("Event: {}", msg.getValue());

            redisTemplate.opsForStream().acknowledge(
                    currencyEventKey,
                    currencyGroupName,
                    msg.getId()
            );
        });
    }


}
