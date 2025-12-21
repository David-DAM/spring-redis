package com.davinchicoder.spring.redis.domain;

import lombok.Data;

@Data
public class Prices {

    private String symbol;
    private Double price;
    private String currency;
    private String exchange;
    private String timestamp;
    private String timezone;
    private String type;
    private String source;

}
