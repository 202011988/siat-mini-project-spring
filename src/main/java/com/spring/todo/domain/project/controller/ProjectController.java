package com.spring.todo.domain.project.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.todo.domain.project.dto.ProjectDTO;
import com.spring.todo.domain.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProjectController {
	private final ProjectService projectService;
	
	// select user에 관한 모든 프로젝트 출력
	@GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUserId() {
        List<ProjectDTO> projects = projectService.getAllProjectsByUserId(1L);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

	// Create
	@PostMapping("/projects")
	public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {	
		// userId는 아직 세션처리를 안했기 때문에 일단 테스트용으로 둠.. 
		ProjectDTO project = projectService.createProject(projectDTO, 1L);
		return new ResponseEntity<>(project, HttpStatus.CREATED);
	}
	
	// Delete
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Update
    @PutMapping("/projects/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        try {
            ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
