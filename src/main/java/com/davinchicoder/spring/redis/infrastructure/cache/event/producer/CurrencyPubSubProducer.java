package com.davinchicoder.spring.redis.infrastructure.cache.event.producer;

import com.davinchicoder.spring.redis.domain.CurrencyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyPubSubProducer {

    @Value("${app.redis.currency.keys.notifications}")
    private String notificationChannelKey;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void publishCurrencyEvent(CurrencyEvent currencyEvent) {

        log.info("Publishing event pub: {}", currencyEvent);
        redisTemplate.convertAndSend(
                notificationChannelKey,
                objectMapper.writeValueAsString(currencyEvent)
        );
    }


}
