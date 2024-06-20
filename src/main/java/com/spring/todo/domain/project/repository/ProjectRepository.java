package com.spring.todo.domain.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.todo.domain.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByUserId(Long userId);
}
