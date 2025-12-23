package com.davinchicoder.spring.redis.infrastructure.cache.config;

import com.davinchicoder.spring.redis.infrastructure.cache.event.consumer.CurrencyStreamListener;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCurrencyStreamRegister {

    @Value("${app.redis.currency.keys.events}")
    private String currencyEventKey;

    @Value("${app.redis.currency.keys.group-name}")
    private String currencyGroupName;

    @Value("${app.redis.currency.keys.instance-name}")
    private String currencyInstanceName;

    private final RedisTemplate<String, String> redisTemplate;
    private final StreamMessageListenerContainer<String, MapRecord<String, String, String>> container;
    private final CurrencyStreamListener listener;

    private Subscription subscription;

    @PostConstruct
    public void start() {

        try {

            if (Boolean.FALSE.equals(redisTemplate.hasKey(currencyEventKey))) {
                redisTemplate.opsForStream().add(
                        currencyEventKey,
                        Map.of("init", "true")
                );
            }

            redisTemplate.opsForStream().createGroup(
                    currencyEventKey,
                    ReadOffset.latest(),
                    currencyGroupName
            );
        } catch (RedisSystemException e) {
            if (e.getCause() instanceof io.lettuce.core.RedisBusyException) {
                log.debug("Redis is busy, group created previously");
            } else {
                log.error("Error creating currency stream group", e);
                throw e;
            }
        } catch (Exception e) {
            log.error("Error starting currency stream listener", e);
            throw e;
        }

        subscription = container.receive(
                Consumer.from(currencyGroupName, currencyInstanceName),
                StreamOffset.create(currencyEventKey, ReadOffset.lastConsumed()),
                listener
        );

        container.start();

    }

    @PreDestroy
    public void stop() {
        if (subscription != null) {
            subscription.cancel();
        }
        container.stop();
    }
}
