package com.spring.todo.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}