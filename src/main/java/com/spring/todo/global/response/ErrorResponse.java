package com.spring.todo.global.response;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private final int statusCode;
    private final String errorMessage;

    public ErrorResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
        this.errorMessage = errorCode.getMessage();
    }
    
    public ErrorResponse(ErrorCode errorCode, String customMessage) {
        this.statusCode = errorCode.getStatusCode();
        this.errorMessage = customMessage;
    }   
}