package com.spring.todo.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.todo.domain.user.dto.LoginRequest;
import com.spring.todo.domain.user.dto.LoginResponse;
import com.spring.todo.domain.user.dto.UserDTO;
import com.spring.todo.domain.user.exception.DuplicateUserEmailException;
import com.spring.todo.domain.user.exception.InvalidCredentialsException;
import com.spring.todo.domain.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    
    // Create ((회원가입) url 추후 변경 사항 /api/users -> "/api/sign-up")
    @PostMapping("/api/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws DuplicateUserEmailException {
        UserDTO user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
    // 로그인
    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws InvalidCredentialsException {
        LoginResponse loginResponse = userService.login(loginRequest.getEmail(), loginRequest.getPassword(), response);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
    
    // 로그아웃    
    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}