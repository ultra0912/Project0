package com.SCBank.exception;

public class BusinessException extends Exception {

    public BusinessException() {
    }

    public BusinessException (final String message) {
        super(message);
    }
}
