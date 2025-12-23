package com.davinchicoder.spring.redis.infrastructure.cache.event.producer;

import com.davinchicoder.spring.redis.domain.CurrencyEvent;
import com.davinchicoder.spring.redis.infrastructure.cache.mapper.CurrencyCacheMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CurrencyStreamProducer {

    @Value("${app.redis.currency.keys.events}")
    private String currencyEventKey;
    private final RedisTemplate<String, String> redisTemplate;
    private final CurrencyCacheMapper mapper;

    public void publishCurrencyEvent(CurrencyEvent event) {

        log.info("Publishing event stream: {}", event);

        redisTemplate.opsForStream().add(
                StreamRecords.newRecord()
                        .in(currencyEventKey)
                        .ofMap(mapper.mapCurrencyEventToHashMap(event))
        );
    }


}
