package com.example.demo.exception;

import lombok.Getter;

@Getter
public class AsgBusinessException extends RuntimeException{
    private final ExceptionType exceptionType;
    private String detail;

    public AsgBusinessException(ExceptionType exceptionType, String detail) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.detail = detail;
    }

    public AsgBusinessException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }
}
