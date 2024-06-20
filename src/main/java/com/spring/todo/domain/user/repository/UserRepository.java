package com.spring.todo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.todo.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
