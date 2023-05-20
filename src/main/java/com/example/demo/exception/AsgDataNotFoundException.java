package com.example.demo.exception;

import lombok.Getter;

@Getter
public class AsgDataNotFoundException extends RuntimeException{
    private final ExceptionType exceptionType;
    private String detail;

    public AsgDataNotFoundException(ExceptionType exceptionType, String detail) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.detail = detail;
    }

    public AsgDataNotFoundException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

}
