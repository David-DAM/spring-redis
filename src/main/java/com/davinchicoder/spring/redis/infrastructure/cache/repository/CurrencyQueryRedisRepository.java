package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.infrastructure.cache.entity.CurrencyCacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyQueryRedisRepository extends CrudRepository<CurrencyCacheEntity, String> {
}
