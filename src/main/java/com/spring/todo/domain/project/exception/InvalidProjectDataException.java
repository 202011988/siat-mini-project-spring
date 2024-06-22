package com.spring.todo.domain.project.exception;

public class InvalidProjectDataException extends RuntimeException {
	public InvalidProjectDataException(String message) {
        super(message);
    }
}