package com.spring.todo.domain.project.entity;

import com.spring.todo.domain.task.entity.Task;
import com.spring.todo.domain.user.entity.User;
import com.spring.todo.global.utill.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Projects")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

//    @Column(nullable = false)
//    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;

    // Getters and Setters
}
