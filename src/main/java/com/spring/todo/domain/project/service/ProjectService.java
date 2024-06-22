package com.spring.todo.domain.project.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.todo.domain.project.dto.ProjectDTO;
import com.spring.todo.domain.project.dto.SimpleProjectDTO;
import com.spring.todo.domain.project.entity.Project;
import com.spring.todo.domain.project.repository.ProjectRepository;
import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.domain.user.entity.User;
import com.spring.todo.domain.user.repository.UserRepository;
import com.spring.todo.global.dto.PageRequestDTO;
import com.spring.todo.global.dto.PageResponseDTO;
import com.spring.todo.global.utill.DateUtils;
import com.spring.todo.global.utill.EntityDtoMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectService extends EntityDtoMapper<ProjectDTO, Project, User> {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    
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
        return entityToDTO(savedProject);
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

        if (projectDTO.getName() != null && !projectDTO.getName().equals(project.getName())) {
            project.setName(projectDTO.getName());
        }

        if (projectDTO.getDescription() != null && !projectDTO.getDescription().equals(project.getDescription())) {
            project.setDescription(projectDTO.getDescription());
        }

        Project updatedProject = projectRepository.save(project);
        return entityToDTO(updatedProject);
    }
    
    @Transactional(readOnly = true)
    public PageResponseDTO<TaskDTO, Task, ProjectDTO> getProjectDetails(Long projectId, PageRequestDTO pageRequestDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("해당 프로젝트가 존재하지 않습니다."));

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize());
        List<Task> tasks = project.getTasks();
        Page<Task> taskPage = new PageImpl<>(tasks, pageable, tasks.size());

        PageResponseDTO<TaskDTO, Task, ProjectDTO> responseDTO = new PageResponseDTO<>(taskPage, EntityDtoMapper::entityToTaskDTO);
        responseDTO.setProject(entityToDTO(project));

        return responseDTO;
    }
    
    private SimpleProjectDTO entityToSimpleDTO(Project project) {
        return SimpleProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }
    
    @Override
    public ProjectDTO entityToDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(DateUtils.formatLocalDateTime(project.getCreatedAt()))
                .updatedAt(DateUtils.formatLocalDateTime(project.getUpdatedAt()))
                .build();
    }
    
    @Override
    public Project dtoToEntity(ProjectDTO projectDTO, User user) {
        return Project.builder()
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .user(user)
                .build();
    }
                         
}