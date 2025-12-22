package com.davinchicoder.spring.redis.infrastructure.scheduler.client;

import java.util.List;

public record CurrencyResponse(
        String status,
        List<CurrencyClientDto> currencies
) {
}
