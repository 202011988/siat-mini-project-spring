package com.spring.todo.domain.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.todo.domain.task.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
}