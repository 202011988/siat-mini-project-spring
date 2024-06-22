package com.spring.todo.domain.task.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.todo.domain.project.entity.Project;
import com.spring.todo.domain.project.exception.ProjectNotFoundException;
import com.spring.todo.domain.project.repository.ProjectRepository;
import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.domain.task.exception.InvalidTaskDataException;
import com.spring.todo.domain.task.exception.TaskNotFoundException;
import com.spring.todo.domain.task.repository.TaskRepository;
import com.spring.todo.global.utill.DateUtils;
import com.spring.todo.global.utill.EntityDtoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service 
public class TaskService extends EntityDtoMapper<TaskDTO, Task, Project> {
	private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO, Long projectId) throws InvalidTaskDataException, ProjectNotFoundException {
    	log.info("새로운 Task를 생성합니다: {}", taskDTO);
    	validateTaskDTO(taskDTO);
    	
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("해당 Project가 존재하지 않습니다."));
        
        Task task = dtoToEntity(taskDTO, project);
        Task savedTask = taskRepository.save(task);
        return entityToDTO(savedTask);
    }
    
    @Transactional(readOnly = true)
    public TaskDTO getTask(Long taskId) throws TaskNotFoundException {
    	log.info("Task ID {}에 대한 정보를 가져옵니다.", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("해당 Task가 존재하지 않습니다."));
        
        return entityToDTO(task);
    }
    
    @Transactional
    public void deleteTask(Long taskId) throws TaskNotFoundException {
    	log.info("Task ID {}를 삭제합니다.", taskId);
    	
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("해당 Task가 존재하지 않습니다."));
        taskRepository.delete(task);
    }
    
    @Transactional
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) throws InvalidTaskDataException, TaskNotFoundException {
    	log.info("Task ID {}를 업데이트합니다: {}", taskId, taskDTO);
    	validateTaskDTO(taskDTO);
    	
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("해당 Task가 존재하지 않습니다."));               

        if (taskDTO.getTitle() != null) {
            task.setTitle(taskDTO.getTitle());
        }

        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }

        if (taskDTO.getDueDate() != null) {
            task.setDueDate(LocalDate.parse(taskDTO.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        if (taskDTO.getStatus() != null) {
            task.setStatus(taskDTO.getStatus());
        }

        Task updatedTask = taskRepository.save(task);
        return entityToDTO(updatedTask);
    }

    @Override
    public Task dtoToEntity(TaskDTO taskDTO, Project project) {
        LocalDate dueDate = DateUtils.parseLocalDate(taskDTO.getDueDate());

        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .dueDate(dueDate)
                .status(taskDTO.getStatus())
                .project(project)
                .build();
    }

    @Override
    public TaskDTO entityToDTO(Task task) {
        return EntityDtoMapper.entityToTaskDTO(task);
    }
    
    private void validateTaskDTO(TaskDTO taskDTO) {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
            log.error("Task 제목이 유효하지 않습니다: {}", taskDTO.getTitle());
            throw new InvalidTaskDataException("Task 제목은 필수입니다.");
        }
        if (taskDTO.getDescription() == null || taskDTO.getDescription().isEmpty()) {
            log.error("Task 설명이 유효하지 않습니다: {}", taskDTO.getDescription());
            throw new InvalidTaskDataException("Task 설명은 필수입니다.");
        }
        if (taskDTO.getDueDate() == null || taskDTO.getDueDate().isEmpty()) {
            log.error("Task 마감일이 유효하지 않습니다: {}", taskDTO.getDueDate());
            throw new InvalidTaskDataException("Task 마감일은 필수입니다.");
        }
        if (taskDTO.getStatus() == null) {
            log.error("Task 상태가 유효하지 않습니다: {}", taskDTO.getStatus());
            throw new InvalidTaskDataException("Task 상태는 필수입니다.");
        }
    }

}
