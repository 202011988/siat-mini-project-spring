package com.spring.todo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.todo.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
}
