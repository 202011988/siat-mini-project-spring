package com.spring.todo.domain.step.entity;

import com.spring.todo.domain.task.entity.Status;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.global.converter.StatusConverter;
import com.spring.todo.global.utill.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Steps")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Step extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Task task;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Convert(converter = StatusConverter.class)
    @Column(nullable = false)
    private Status status = Status.PENDING;
}