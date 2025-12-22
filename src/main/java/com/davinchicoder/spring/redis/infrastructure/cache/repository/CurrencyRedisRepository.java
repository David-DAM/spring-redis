package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import com.davinchicoder.spring.redis.infrastructure.cache.mapper.CurrencyCacheMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class CurrencyRedisRepository {

    private final CurrencyQueryRedisRepository currencyQueryRedisRepository;
    private final CurrencyCacheMapper mapper;

    public Optional<Currency> getById(String code) {
        return currencyQueryRedisRepository.findById(code).map(mapper::mapToDomain);
    }

    public List<Currency> getAll() {
        return StreamSupport.stream(currencyQueryRedisRepository.findAll().spliterator(), false)
                .map(mapper::mapToDomain)
                .toList();
    }

    public void upsert(Currency currency) {
        currencyQueryRedisRepository.save(mapper.mapToEntity(currency));
    }

    public void upsertAll(List<Currency> currencies) {
        List<CurrencyCacheEntity> cacheEntities = currencies.stream().map(mapper::mapToEntity).toList();
        currencyQueryRedisRepository.saveAll(cacheEntities);
    }

    public void delete(String code) {
        currencyQueryRedisRepository.deleteById(code);
    }

    public void deleteAll() {
        currencyQueryRedisRepository.deleteAll();
    }


}
