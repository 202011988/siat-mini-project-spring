package com.spring.todo.domain.project.entity;

import com.spring.todo.domain.user.entity.User;
import com.spring.todo.global.utill.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString // TODO: 없애기
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
