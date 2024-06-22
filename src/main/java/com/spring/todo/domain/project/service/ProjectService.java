package com.spring.todo.domain.project.service;

import java.util.List;
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
import com.spring.todo.domain.project.exception.InvalidProjectDataException;
import com.spring.todo.domain.project.exception.ProjectNotFoundException;
import com.spring.todo.domain.project.repository.ProjectRepository;
import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.domain.user.entity.User;
import com.spring.todo.domain.user.exception.UserNotFoundException;
import com.spring.todo.domain.user.repository.UserRepository;
import com.spring.todo.global.dto.PageRequestDTO;
import com.spring.todo.global.dto.PageResponseDTO;
import com.spring.todo.global.utill.DateUtils;
import com.spring.todo.global.utill.EntityDtoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectService extends EntityDtoMapper<ProjectDTO, Project, User> {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true) 
    public List<SimpleProjectDTO> getAllProjectsByUserId(Long userId) {
    	log.info("User ID {}에 대한 모든 Project를 가져옵니다.", userId);
    	
        return projectRepository.findByUserId(userId).stream()
                .map(this::entityToSimpleDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO, Long userId) throws InvalidProjectDataException, UserNotFoundException {
    	log.info("새로운 Project를 생성합니다: {}", projectDTO);
        validateProjectDTO(projectDTO);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 User가 존재하지 않습니다."));
      
        Project project = dtoToEntity(projectDTO, user);
        Project savedProject = projectRepository.save(project);
        return entityToDTO(savedProject);
    }
    
    @Transactional
    public void deleteProject(Long projectId) throws ProjectNotFoundException {
    	log.info("Project ID {}를 삭제합니다.", projectId);
    	
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("해당 Project가 존재하지 않습니다."));
        projectRepository.delete(project);
    }
    
    @Transactional
    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO) throws InvalidProjectDataException, ProjectNotFoundException {
    	log.info("Project ID {}를 업데이트합니다: {}", projectId, projectDTO);
    	validateProjectDTO(projectDTO);
    	
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("해당 Project가 존재하지 않습니다."));       

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
    public PageResponseDTO<TaskDTO, Task, ProjectDTO> getProjectDetails(Long projectId, PageRequestDTO pageRequestDTO) throws ProjectNotFoundException {
    	log.info("Project ID {}에 대한 상세 정보와 Task를 가져옵니다.", projectId);
    	
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("해당 Project가 존재하지 않습니다."));

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
    
    private void validateProjectDTO(ProjectDTO projectDTO) {
        if (projectDTO.getName() == null || projectDTO.getName().isEmpty()) {
            log.error("프로젝트 이름이 유효하지 않습니다: {}", projectDTO.getName());
            throw new InvalidProjectDataException("프로젝트 이름은 필수입니다.");
        }
        if (projectDTO.getDescription() == null || projectDTO.getDescription().isEmpty()) {
            log.error("프로젝트 설명이 유효하지 않습니다: {}", projectDTO.getDescription());
            throw new InvalidProjectDataException("프로젝트 설명은 필수입니다.");
        }
    }
                         
}