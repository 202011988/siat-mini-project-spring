package com.spring.todo.domain.project.dto;

import java.util.List;

import com.spring.todo.domain.task.dto.TaskDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;
    private List<TaskDTO> tasks;
}