package com.spring.todo.domain.step.repository;

import com.spring.todo.domain.step.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {

}
