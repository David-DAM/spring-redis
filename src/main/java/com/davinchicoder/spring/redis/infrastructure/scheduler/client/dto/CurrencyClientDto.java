package com.davinchicoder.spring.redis.infrastructure.scheduler.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record CurrencyClientDto(
        String code,
        String symbol,
        String name,
        @JsonAlias("rate_per_usd")
        String ratePerUsd,
        @JsonAlias("last_modified")
        String lastModified
) {
}
