package com.spring.todo.domain.user.exception;

public class CookieUserEmailNotFoundException extends RuntimeException {
    public CookieUserEmailNotFoundException(String message) {
        super(message);
    }
}
