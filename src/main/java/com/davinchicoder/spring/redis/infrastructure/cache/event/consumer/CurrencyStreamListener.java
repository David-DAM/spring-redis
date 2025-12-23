package com.davinchicoder.spring.redis.infrastructure.cache.event.consumer;

import com.davinchicoder.spring.redis.domain.CurrencyEvent;
import com.davinchicoder.spring.redis.infrastructure.cache.event.mapper.CurrencyEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CurrencyStreamListener implements StreamListener<String, MapRecord<String, String, String>> {


    @Value("${app.redis.currency.keys.group-name}")
    private String currencyGroupName;

    @Value("${app.redis.currency.keys.events}")
    private String currencyEventKey;


    private final RedisTemplate<String, String> redisTemplate;
    private final CurrencyEventMapper mapper;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        try {
            log.info("Event: {}", message.getValue());


            CurrencyEvent event = mapper.mapToCurrencyEvent(message.getValue());

            log.info("Received event: {}", event);

            redisTemplate.opsForStream().acknowledge(
                    currencyEventKey,
                    currencyGroupName,
                    message.getId()
            );
        } catch (Exception e) {
            // NO ACK → retry
            log.error("Error processing currency event", e);
        }
    }
}

