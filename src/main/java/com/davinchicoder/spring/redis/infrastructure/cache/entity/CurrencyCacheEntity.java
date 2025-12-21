package com.davinchicoder.spring.redis.infrastructure.cache.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("currency")
@Data
public class CurrencyCacheEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3366787961443564799L;

    @Id
    private String code;
    private String symbol;
    private String name;
    private Double ratePerUsd;
    private LocalDateTime lastModified;

}
