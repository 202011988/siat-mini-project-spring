package com.spring.todo.domain.task.dto;

import com.spring.todo.domain.task.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class TaskDTO {
	private Long id;
    private String title;
    private String description;
    private String dueDate;
    private Status status;
    
    public TaskDTO() {
        this.status = Status.PENDING;
    }
}