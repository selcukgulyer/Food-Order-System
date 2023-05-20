package com.example.demo.exception;

import lombok.Getter;

@Getter
public class AsgRabbitException extends RuntimeException {
    private final ExceptionType exceptionType;
    private String detail;

    public AsgRabbitException(ExceptionType exceptionType, String detail) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.detail = detail;
    }

    public AsgRabbitException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }
}
