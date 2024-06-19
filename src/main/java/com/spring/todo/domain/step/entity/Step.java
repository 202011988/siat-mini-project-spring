package com.spring.todo.domain.step.entity;

import com.spring.todo.domain.task.entity.Status;
import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.global.utill.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "Steps")
public class Step extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stepId;

    @ManyToOne
    @JoinColumn(name = "taskId", nullable = false)
    private Task task;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

//    @Column(nullable = false)
//    private LocalDateTime createdAt;
//
//    @Column(nullable = false)
//    private LocalDateTime updatedAt;

    // Getters and Setters
}