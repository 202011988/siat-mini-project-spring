package com.spring.todo.domain.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.todo.domain.project.dto.ProjectDTO;
import com.spring.todo.domain.project.dto.SimpleProjectDTO;
import com.spring.todo.domain.project.entity.Project;
import com.spring.todo.domain.project.repository.ProjectQueryDSLRepository;
import com.spring.todo.domain.project.repository.ProjectRepository;
import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.domain.user.entity.User;
import com.spring.todo.domain.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectQueryDSLRepository projectQueryDSLRepository;
    private final UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional(readOnly = true) 
    public List<SimpleProjectDTO> getAllProjectsByUserId(Long userId) {
        return projectRepository.findByUserId(userId).stream()
                .map(this::entityToSimpleDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
      
        Project project = dtoToEntity(projectDTO, user);
        Project savedProject = projectRepository.save(project);
        return entityToDetailedDTO(savedProject);
    }
    
    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("해당 프로젝트가 존재하지 않습니다."));
        projectRepository.delete(project);
    }
    
    @Transactional
    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("해당 프로젝트가 존재하지 않습니다."));

        boolean isNameChanged = projectDTO.getName() != null && !projectDTO.getName().equals(project.getName());
        boolean isDescriptionChanged = projectDTO.getDescription() != null && !projectDTO.getDescription().equals(project.getDescription());

        long updateCount = 0;
        if (isNameChanged || isDescriptionChanged) {
            updateCount = projectQueryDSLRepository.updateProject(
                    projectId,
                    isNameChanged ? projectDTO.getName() : null,
                    isDescriptionChanged ? projectDTO.getDescription() : null
            );
            if (updateCount > 0) {
                projectRepository.flush();
            }
        } else {
            throw new NoSuchElementException("업데이트할 값이 없습니다.");
        }

        entityManager.clear();

        return entityToDetailedDTO(projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("해당 프로젝트가 존재하지 않습니다.")));
    }
    
    @Transactional(readOnly = true)
    public ProjectDTO getProjectDetails(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("해당 프로젝트가 존재하지 않습니다."));

        ProjectDTO projectDTO = entityToDetailedDTO(project);
        List<TaskDTO> taskDTOs = project.getTasks().stream()
                .map(this::entityToTaskDTO)
                .collect(Collectors.toList());
        projectDTO.setTasks(taskDTOs);

        return projectDTO;
    }
    
    private Project dtoToEntity(ProjectDTO projectDTO, User user) {
        return Project.builder()
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .user(user)
                .build();
    }
    
    private SimpleProjectDTO entityToSimpleDTO(Project project) {
        return SimpleProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }
    
    private ProjectDTO entityToDetailedDTO(Project project) {
    	return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(formatLocalDateTime(project.getCreatedAt()))
                .updatedAt(formatLocalDateTime(project.getUpdatedAt()))
                .build();
    }
    
    private TaskDTO entityToTaskDTO(Task task) {
    	String dueDate = formatDateTime(task.getDueDate());

        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(dueDate)
                .status(task.getStatus())
                .build();
    }
    
    private String formatLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }
    
    private String formatDateTime(LocalDate date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}