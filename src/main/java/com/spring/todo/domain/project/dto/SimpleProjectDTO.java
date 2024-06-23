package com.spring.todo.domain.project.dto;

import lombok.Builder;
import lombok.Getter;

@Getter // getter 없으면 오류남... why??
@Builder
public class SimpleProjectDTO {
    private Long id;
    private String name;
}