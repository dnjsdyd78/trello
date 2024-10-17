package com.sparta.trelloproject.domain.user.entity;

import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.rmi.ServerException;
import java.util.Collections;
import java.util.List;

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

    public static User fromAuthUser(AuthUser authUser) {
        UserRole role = UserRole.of(
                authUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
//                        .orElseThrow(() -> new ServerException("권한이 없습니다."))
                        .orElseThrow(() -> new IllegalArgumentException("권한이 없습니다."))
        );
//        return new User(authUser.getId(), authUser.getEmail(), role);
        return null;
    }

    // 사용자의 권한을 GrantedAuthority로 변환하여 반환
    public List<SimpleGrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
}
