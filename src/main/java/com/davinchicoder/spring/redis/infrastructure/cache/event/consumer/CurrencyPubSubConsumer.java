package com.davinchicoder.spring.redis.infrastructure.cache.event.consumer;

import com.davinchicoder.spring.redis.domain.CurrencyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class CurrencyPubSubConsumer implements MessageListener {

    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CurrencyEvent event = objectMapper.readValue(message.getBody(), CurrencyEvent.class);
        log.info("Received event: {}", event);
    }
}

