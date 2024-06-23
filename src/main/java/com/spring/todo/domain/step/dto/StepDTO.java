package com.spring.todo.domain.step.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StepDTO {
    private Long id;
    private String title;
    private String description;
}
