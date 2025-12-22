package com.davinchicoder.spring.redis.infrastructure.scheduler.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record CurrencyClientDto(
        String code,
        String symbol,
        String name,
        @JsonProperty("rate_per_usd")
        Double ratePerUsd,
        @JsonProperty("last_modified")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime lastModified
) {
}
