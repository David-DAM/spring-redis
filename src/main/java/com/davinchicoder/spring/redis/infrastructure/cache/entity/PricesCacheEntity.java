package com.davinchicoder.spring.redis.infrastructure.cache.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;

@RedisHash("prices")
@Data
public class PricesCacheEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3366787961443564799L;

    @Id
    private String currency;
    private String symbol;
    private Double price;
    private String exchange;
    private String timestamp;
    private String timezone;
    private String type;
    private String source;

}
