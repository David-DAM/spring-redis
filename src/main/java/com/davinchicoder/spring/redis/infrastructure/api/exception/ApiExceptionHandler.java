package com.davinchicoder.spring.redis.infrastructure.api.exception;

import com.davinchicoder.spring.redis.domain.CurrencyNotFoundException;
import com.davinchicoder.spring.redis.domain.TooManyRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(CurrencyNotFoundException.class)
    public ErrorMessage notFound(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception.getMessage(), request.getServletPath(), String.valueOf(System.currentTimeMillis()));
    }

    @ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
    @ResponseBody
    @ExceptionHandler(TooManyRequestException.class)
    public ErrorMessage tooManyRequest(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception.getMessage(), request.getServletPath(), String.valueOf(System.currentTimeMillis()));
    }

}
