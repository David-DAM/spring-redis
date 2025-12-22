package com.davinchicoder.spring.redis.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Currency implements Serializable {

    @Serial
    private static final long serialVersionUID = 8934567812345678901L;

    private String code;
    private String symbol;
    private String name;
    private Double ratePerUsd;
    private LocalDateTime lastModified;

}
