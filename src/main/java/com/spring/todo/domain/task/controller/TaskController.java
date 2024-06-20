package com.spring.todo.domain.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.service.TaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TaskController {
	private final TaskService taskService;
	
	@PostMapping("/api/projects/{projectId}")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO, projectId);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
}
