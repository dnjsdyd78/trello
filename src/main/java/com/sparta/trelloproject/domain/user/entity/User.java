package com.sparta.trelloproject.domain.user.entity;

import com.sparta.trelloproject.domain.user.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String email;


    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // 유저 권한 enum
    public enum Role {
        USER, // 일반 유저
        ADMIN // 관리자 (워크스페이스 생성 가능, 다른 유저를 워크스페이스 관리자로 설정)
    }
}
