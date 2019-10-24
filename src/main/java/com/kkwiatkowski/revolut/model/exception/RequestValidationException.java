package com.kkwiatkowski.revolut.model.exception;

public class RequestValidationException extends RuntimeException {
    public RequestValidationException() {
    }

    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
