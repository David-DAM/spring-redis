package com.davinchicoder.spring.redis.infrastructure.api.dto;

import java.time.LocalDateTime;

public record CurrencyDto(
        String code,
        String symbol,
        String name,
        Double ratePerUsd,
        LocalDateTime lastModified
) {
}
