package com.davinchicoder.spring.redis.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Currency {

    private String code;
    private String symbol;
    private String name;
    private Double ratePerUsd;
    private LocalDateTime lastModified;

}
