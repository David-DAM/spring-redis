package com.davinchicoder.spring.redis.infrastructure.cache.repository;

import com.davinchicoder.spring.redis.infrastructure.cache.entity.PricesCacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricesQueryRedisRepository extends CrudRepository<PricesCacheEntity, String> {
}
