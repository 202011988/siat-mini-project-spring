package com.spring.todo.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.todo.domain.user.dto.UserDTO;
import com.spring.todo.domain.user.entity.User;
import com.spring.todo.domain.user.exception.DuplicateUserEmailException;
import com.spring.todo.domain.user.exception.InvalidCredentialsException;
import com.spring.todo.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
  
    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws DuplicateUserEmailException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
        	log.error("DuplicateUserEmailException: 이미 존재하는 이메일입니다: {}", userDTO.getEmail());
            throw new DuplicateUserEmailException("이미 존재하는 이메일입니다: " + userDTO.getEmail());
        }

        User user = dtoToEntity(userDTO);       
        User savedUser = userRepository.save(user);
        
        log.info("새로운 사용자 생성: {}", savedUser.getEmail());
        return entityToDTO(savedUser);
    }
    
    // 로그인
    @Transactional(readOnly = true)
    public boolean login(String email, String password) throws InvalidCredentialsException {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);

        if(user.isPresent()) {
        	log.info("로그인 성공: {}", email);
        	return true;
        } else {
        	log.error("InvalidCredentialsException: 아이디 및 비밀번호가 일치하지 않습니다: {}", email);
        	throw new InvalidCredentialsException("아이디 및 비밀번호가 일치하지 않습니다.");
        }
    }

    private User dtoToEntity(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .build();
    }

    private UserDTO entityToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }

}