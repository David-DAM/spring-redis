package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import com.davinchicoder.spring.redis.infrastructure.cache.mapper.CurrencyCacheMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CurrencyPipelineRepository {

    private final RedisTemplate<String, CurrencyCacheEntity> redisTemplate;
    private final CurrencyCacheMapper mapper;

    public void saveAllPipelined(List<Currency> currencies) {
        redisTemplate.executePipelined((RedisCallback<?>) (connection) -> {
            currencies.stream()
                    .forEach(currency -> {
                        String keyString = "currency:".concat(currency.getCode());
                        CurrencyCacheEntity entity = mapper.mapToEntity(currency);

                        connection.stringCommands().set(
                                redisTemplate.getStringSerializer().serialize(keyString),
                                ((RedisSerializer<CurrencyCacheEntity>) redisTemplate.getValueSerializer()).serialize(entity)
                        );
                    });
            return null;
        });
    }


}
