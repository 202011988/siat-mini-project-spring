package com.spring.todo.domain.task.entity;

import com.spring.todo.domain.project.entity.Project;
import com.spring.todo.domain.user.entity.User;
import com.spring.todo.global.utill.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "Tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString // TODO: 없애기
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Project project;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
}
