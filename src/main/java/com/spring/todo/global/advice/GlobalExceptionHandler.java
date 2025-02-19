package com.spring.todo.global.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.todo.domain.project.exception.InvalidProjectDataException;
import com.spring.todo.domain.project.exception.ProjectNotFoundException;
import com.spring.todo.domain.task.exception.InvalidTaskDataException;
import com.spring.todo.domain.task.exception.TaskNotFoundException;
import com.spring.todo.domain.user.exception.CookieUserEmailNotFoundException;
import com.spring.todo.domain.user.exception.DuplicateUserEmailException;
import com.spring.todo.domain.user.exception.InvalidCredentialsException;
import com.spring.todo.domain.user.exception.UserNotFoundException;
import com.spring.todo.global.response.ErrorCode;
import com.spring.todo.global.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("User_NotFound_Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.USER_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFoundException(ProjectNotFoundException ex) {
        log.error("Project_NotFound_Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PROJECT_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(ErrorCode.PROJECT_NOT_FOUND.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(InvalidProjectDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProjectDataException(InvalidProjectDataException ex) {
        log.error("Invalid_ProjectData_Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_PROJECT_DATA, ex.getMessage());
        return ResponseEntity.status(ErrorCode.INVALID_PROJECT_DATA.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex) {
        log.error("Task_NotFound_Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(ErrorCode.NOT_FOUND.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(InvalidTaskDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTaskDataException(InvalidTaskDataException ex) {
        log.error("Invalid_TaskData_Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_TASK_DATA, ex.getMessage());
        return ResponseEntity.status(ErrorCode.INVALID_TASK_DATA.getStatusCode()).body(errorResponse);
    }
    
    @ExceptionHandler(DuplicateUserEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserEmailException(DuplicateUserEmailException ex) {
        log.error("Duplicate_User_Email_Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.DUPLICATE_USER_EMAIL, ex.getMessage());
        return ResponseEntity.status(ErrorCode.DUPLICATE_USER_EMAIL.getStatusCode()).body(errorResponse);
    }
    
    @ExceptionHandler(CookieUserEmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCookieUserEmailNotFoundException(CookieUserEmailNotFoundException ex) {
        log.error("Cookie_User_Email_Not_Found_Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.COOKIE_USER_EMAIL_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(ErrorCode.COOKIE_USER_EMAIL_NOT_FOUND.getStatusCode()).body(errorResponse);
    }
    
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.error("Invalid_Credentials_Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_CREDENTIALS, ex.getMessage());
        return ResponseEntity.status(ErrorCode.INVALID_CREDENTIALS.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatusCode()).body(errorResponse);
    }
}