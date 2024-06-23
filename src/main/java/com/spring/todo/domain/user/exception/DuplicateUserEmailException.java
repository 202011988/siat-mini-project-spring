package com.spring.todo.domain.user.exception;

public class DuplicateUserEmailException extends RuntimeException {
    public DuplicateUserEmailException(String message) {
        super(message);
    }
}