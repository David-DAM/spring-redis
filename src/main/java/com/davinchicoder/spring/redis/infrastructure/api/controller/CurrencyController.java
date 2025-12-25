package com.davinchicoder.spring.redis.infrastructure.api.controller;

import com.davinchicoder.api.CurrencyApi;
import com.davinchicoder.api.dto.CurrencyDto;
import com.davinchicoder.spring.redis.application.CurrencyService;
import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.api.mapper.CurrencyDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/{version}/currency", version = "v1")
@RequiredArgsConstructor
public class CurrencyController implements CurrencyApi {

    private final CurrencyService service;
    private final CurrencyDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getCurrencies(@PathVariable String version, @RequestHeader String userId) {
        List<CurrencyDto> currencyDtos = service.getAll(userId).stream().map(mapper).toList();
        return ResponseEntity.ok(currencyDtos);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CurrencyDto> getCurrency(@PathVariable String code, @PathVariable String version, @RequestHeader String userId) {
        Currency currency = service.getById(code, userId);
        CurrencyDto currencyDto = mapper.apply(currency);
        return ResponseEntity.ok(currencyDto);
    }

}
