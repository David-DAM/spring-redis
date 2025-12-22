package com.davinchicoder.spring.redis.infrastructure.scheduler.client;

import com.davinchicoder.spring.redis.domain.Currency;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CurrencyClientMapper implements Function<CurrencyClientDto, Currency> {

    @Override
    public Currency apply(CurrencyClientDto dto) {

        Currency currency = new Currency();
        currency.setCode(dto.code());
        currency.setSymbol(dto.symbol());
        currency.setRatePerUsd(dto.ratePerUsd());
        currency.setLastModified(dto.lastModified());

        return currency;
    }
}
