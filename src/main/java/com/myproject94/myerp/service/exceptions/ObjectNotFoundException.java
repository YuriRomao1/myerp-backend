package com.myproject94.myerp.service.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
