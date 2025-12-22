package com.davinchicoder.spring.redis.infrastructure.api.mapper;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.api.dto.CurrencyDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CurrencyDtoMapper implements Function<Currency, CurrencyDto> {
    @Override
    public CurrencyDto apply(Currency currency) {
        return new CurrencyDto(
                currency.getCode(),
                currency.getSymbol(),
                currency.getName(),
                currency.getRatePerUsd(),
                currency.getLastModified()
        );
    }


}
