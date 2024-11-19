package com.noxinfinity.pdating.Exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public UnauthorizedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
