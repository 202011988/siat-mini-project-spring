package com.spring.todo.domain.project.controller;

import java.util.List;

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
import com.spring.todo.domain.project.dto.SimpleProjectDTO;
import com.spring.todo.domain.project.exception.InvalidProjectDataException;
import com.spring.todo.domain.project.exception.ProjectNotFoundException;
import com.spring.todo.domain.project.service.ProjectService;
import com.spring.todo.domain.task.dto.TaskDTO;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.domain.user.exception.CookieUserEmailNotFoundException;
import com.spring.todo.domain.user.exception.UserNotFoundException;
import com.spring.todo.global.dto.PageRequestDTO;
import com.spring.todo.global.dto.PageResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProjectController {
	private final ProjectService projectService;

	// select user에 관한 모든 프로젝트 출력
    @GetMapping("/api/projects")
    public ResponseEntity<List<SimpleProjectDTO>> getProjectsByUserEmail(HttpServletRequest request) throws CookieUserEmailNotFoundException {
        String userEmail = getUserEmailFromCookies(request);     

        List<SimpleProjectDTO> projects = projectService.getAllProjectsByUserEmail(userEmail);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
	
	// 특정 프로젝트의 상세 정보와 연관된 Task 가져오기 (프로젝트 이름 클릭시 매핑 및 Task 페이징 처리)
	@GetMapping("/api/projects/{projectId}")
	public ResponseEntity<PageResponseDTO<TaskDTO, Task, ProjectDTO>> getProjectDetails(@PathVariable Long projectId, PageRequestDTO pageRequestDTO) throws ProjectNotFoundException {
		PageResponseDTO<TaskDTO, Task, ProjectDTO> projectDetails = projectService.getProjectDetails(projectId, pageRequestDTO);
		return new ResponseEntity<>(projectDetails, HttpStatus.OK);
	}

	// Create
	@PostMapping("/api/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO, HttpServletRequest request) throws CookieUserEmailNotFoundException, InvalidProjectDataException, UserNotFoundException {
        String userEmail = getUserEmailFromCookies(request);

        ProjectDTO project = projectService.createProject(projectDTO, userEmail);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

	// Delete
	@DeleteMapping("/api/projects/{projectid}")
	public ResponseEntity<Void> deleteProject(@PathVariable Long projectid) throws ProjectNotFoundException {
		projectService.deleteProject(projectid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Update
	@PutMapping("/api/projects/{projectid}")
	public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long projectid, @RequestBody ProjectDTO projectDTO) throws InvalidProjectDataException, ProjectNotFoundException {
		ProjectDTO updatedProject = projectService.updateProject(projectid, projectDTO);
		return new ResponseEntity<>(updatedProject, HttpStatus.OK);
	}
	
	private String getUserEmailFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userEmail".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        throw new CookieUserEmailNotFoundException("쿠키에서 사용자 이메일이 존재하지 않습니다.");
    }
}
