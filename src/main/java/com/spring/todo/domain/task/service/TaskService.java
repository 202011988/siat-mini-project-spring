package com.spring.todo.domain.task.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.todo.domain.project.entity.Project;
import com.spring.todo.domain.project.repository.ProjectRepository;
import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.domain.task.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class TaskService {
	private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("해당 프로젝트가 존재하지 않습니다."));

        Task task = dtoToEntity(taskDTO, project);
        Task savedTask = taskRepository.save(task);
        return entityToDTO(savedTask);
    }

    private Task dtoToEntity(TaskDTO taskDTO, Project project) {
        LocalDate dueDate = null;
        if (taskDTO.getDueDate() != null) {
            dueDate = LocalDate.parse(taskDTO.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .dueDate(dueDate)
                .status(taskDTO.getStatus())
                .project(project)
                .build();
    }

    private TaskDTO entityToDTO(Task task) {
        String dueDate = null;
        if (task.getDueDate() != null) {
            dueDate = task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(dueDate)
                .status(task.getStatus())
                .build();
    }
}
