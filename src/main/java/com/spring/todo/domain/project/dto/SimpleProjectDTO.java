package com.spring.todo.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter // getter 없으면 오류남... why??
@Builder
@ToString
public class SimpleProjectDTO {
    private Long id;
    private String name;
    private String description;
}