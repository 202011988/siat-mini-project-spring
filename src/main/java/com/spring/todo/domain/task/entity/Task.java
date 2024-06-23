package com.spring.todo.domain.task.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import com.spring.todo.domain.project.entity.Project;
import com.spring.todo.global.converter.StatusConverter;
import com.spring.todo.global.utill.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Tasks")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString // TODO: 없애기
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Project project;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private LocalDate dueDate;

    @Convert(converter = StatusConverter.class)
    @Column(nullable = false)
    private Status status;
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}