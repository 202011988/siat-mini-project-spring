package com.spring.todo.domain.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.todo.domain.project.exception.ProjectNotFoundException;
import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.exception.InvalidTaskDataException;
import com.spring.todo.domain.task.exception.TaskNotFoundException;
import com.spring.todo.domain.task.service.TaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TaskController {
	private final TaskService taskService;
	
	// create
	@PostMapping("/api/projects/{projectId}")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO) throws InvalidTaskDataException, ProjectNotFoundException {
        TaskDTO createdTask = taskService.createTask(taskDTO, projectId);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
	
	// getTask (task 이름 클릭 시 상세 페이지로 이동 후 해당 task 상세 정보 출력)
    @GetMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        TaskDTO taskDTO = taskService.getTask(taskId);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }
	
	// delete (task 상세페이지에서 삭제 기능 구현)
    @DeleteMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) throws TaskNotFoundException {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // update (task 상세페이지에서 수정 기능 구현 -> 기존 값과 수정된 값 모두 서버에 넘어온다는 가정하에 진행) 
    @PutMapping("/api/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) throws InvalidTaskDataException, TaskNotFoundException {
        TaskDTO updatedTask = taskService.updateTask(taskId, taskDTO);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
	
}