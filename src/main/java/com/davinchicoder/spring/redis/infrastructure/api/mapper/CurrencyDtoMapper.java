package com.davinchicoder.spring.redis.infrastructure.api.mapper;

import com.davinchicoder.api.dto.CurrencyDto;
import com.davinchicoder.spring.redis.domain.Currency;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.function.Function;

@Component
public class CurrencyDtoMapper implements Function<Currency, CurrencyDto> {
    @Override
    public CurrencyDto apply(Currency currency) {

        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCode(currency.getCode());
        currencyDto.setSymbol(currency.getSymbol());
        currencyDto.setName(currency.getName());
        currencyDto.setRatePerUsd(currency.getRatePerUsd());
        currencyDto.setLastModified(currency.getLastModified().atOffset(ZoneOffset.UTC));
        
        return currencyDto;
    }


}
