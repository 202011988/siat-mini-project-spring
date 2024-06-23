package com.spring.todo.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString // TODO: 개발 완료 후 제거하기
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false)
    private String nickname;
}