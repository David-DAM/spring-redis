package com.davinchicoder.spring.redis.infrastructure.scheduler.client;

import java.time.LocalDateTime;

public record CurrencyDto(
        String code,
        String symbol,
        String name,
        Double ratePerUsd,
        LocalDateTime lastModified
) {
}
