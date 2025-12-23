package com.davinchicoder.spring.redis.infrastructure.cache.event.producer;

import com.davinchicoder.spring.redis.domain.CurrencyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
@RequiredArgsConstructor
public class CurrencyPubSubProducer {

    @Value("${app.redis.currency.keys.notifications}")
    private String notificationChannelKey;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void publishCurrencyEvent(CurrencyEvent currencyEvent) {
        redisTemplate.convertAndSend(
                notificationChannelKey,
                objectMapper.writeValueAsString(currencyEvent)
        );
    }


}
