package com.spring.todo.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.todo.domain.user.dto.LoginRequest;
import com.spring.todo.domain.user.dto.UserDTO;
import com.spring.todo.domain.user.exception.DuplicateUserEmailException;
import com.spring.todo.domain.user.exception.InvalidCredentialsException;
import com.spring.todo.domain.user.service.UserService;

import jakarta.servlet.http.Cookie;
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
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws InvalidCredentialsException {
        boolean isAuthenticated = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (isAuthenticated) {
            // 이메일 정보를 쿠키로 설정
            Cookie emailCookie = new Cookie("userEmail", loginRequest.getEmail());
            emailCookie.setHttpOnly(true);
            emailCookie.setPath("/");
            emailCookie.setMaxAge(30 * 60); // 30 minutes

            response.addCookie(emailCookie);          
        } 
       
        return new ResponseEntity<>(HttpStatus.OK);
    }
}