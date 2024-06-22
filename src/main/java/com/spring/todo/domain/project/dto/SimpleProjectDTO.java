package com.spring.todo.domain.project.dto;

import lombok.Builder;

@Builder
public class SimpleProjectDTO {
    private Long id;
    private String name;
}