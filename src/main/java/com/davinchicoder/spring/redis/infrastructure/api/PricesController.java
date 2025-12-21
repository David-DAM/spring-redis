package com.davinchicoder.spring.redis.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/{version}/prices", version = "v1")
@RequiredArgsConstructor
public class PricesController {

    @GetMapping
    public ResponseEntity<String> getPrices() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping
    public ResponseEntity<String> getPrice(String symbol) {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping
    public ResponseEntity<String> getPrice(String symbol, String currency) {
        return ResponseEntity.ok("Hello World!");
    }

}
