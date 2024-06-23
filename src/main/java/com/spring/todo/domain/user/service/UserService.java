package com.spring.todo.domain.user.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.todo.domain.user.dto.UserDTO;
import com.spring.todo.domain.user.entity.User;
import com.spring.todo.domain.user.exception.DuplicateUserEmailException;
import com.spring.todo.domain.user.repository.UserQueryDSLRepository;
import com.spring.todo.domain.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryDSLRepository userQueryDSLRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws DuplicateUserEmailException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateUserEmailException("이미 존재하는 이메일입니다: " + userDTO.getEmail());
        }

        User user = dtoToEntity(userDTO);

        User savedUser = userRepository.save(user);
        return entityToDTO(savedUser);
    }
    
    // 로그인
    @Transactional(readOnly = true)
    public boolean login(String email, String password) {
    	System.out.println("email :" + email);
    	System.out.println("password : " + password);
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.isPresent();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));
        return entityToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));

        boolean isEmailChanged = userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail());
        boolean isPasswordChanged = userDTO.getPassword() != null && !userDTO.getPassword().equals(user.getPassword());
        boolean isNicknameChanged = userDTO.getNickname() != null && !userDTO.getNickname().equals(user.getNickname());

        long updateCount = 0;
        if (isEmailChanged || isPasswordChanged || isNicknameChanged) {
            updateCount = userQueryDSLRepository.updateUser(
                    id,
                    isEmailChanged ? userDTO.getEmail() : null,
                    isPasswordChanged ? userDTO.getPassword() : null,
                    isNicknameChanged ? userDTO.getNickname() : null
            );
            if (updateCount > 0) {
                userRepository.flush();
            }
        } else {
            throw new NoSuchElementException("업데이트할 값이 없습니다.");
        }

        entityManager.clear();

        return entityToDTO(userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자가 존재하지 않습니다.")));

    }

    @Transactional
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));
        return entityToDTO(user);
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


    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));
        userRepository.delete(user);
    }
}
