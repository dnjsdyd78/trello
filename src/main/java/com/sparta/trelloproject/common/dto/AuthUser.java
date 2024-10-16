package com.sparta.trelloproject.common.dto;

import com.sparta.trelloproject.domain.user.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(Long id, String email, UserRole role) {
        this.id = id;
        this.email = email;
        this.authorities = List.of(new SimpleGrantedAuthority(role.name()));
    }
}
