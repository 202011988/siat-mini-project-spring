package com.spring.todo.domain.step.entity;

import com.spring.todo.domain.task.entity.Task;
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

    @Column(nullable = false, length = 25)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
}