package com.davinchicoder.spring.redis.infrastructure.api;

import com.davinchicoder.spring.redis.application.CurrencyService;
import com.davinchicoder.spring.redis.domain.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/{version}/prices", version = "v1")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService service;
    private final CurrencyDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getCurrencies(@PathVariable String version) {
        List<CurrencyDto> currencyDtos = service.getAll().stream().map(mapper).toList();
        return ResponseEntity.ok(currencyDtos);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CurrencyDto> getCurrency(@PathVariable String code, @PathVariable String version) {
        Currency currency = service.getById(code);
        CurrencyDto currencyDto = mapper.apply(currency);
        return ResponseEntity.ok(currencyDto);
    }

}
