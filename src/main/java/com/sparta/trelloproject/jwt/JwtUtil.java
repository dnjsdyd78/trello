package com.sparta.trelloproject.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private static final long EXPIRATION_TIME = 86400000; // 1일 (밀리초)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT 토큰 생성
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰에서 사용자 이메일 추출
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // JWT 토큰에서 사용자 권한 추출
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰에서 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String email = claims.getSubject();  // 토큰에서 이메일 추출
        String role = claims.get("role", String.class);  // 토큰에서 역할 추출

        // 권한 리스트 생성 (권한 정보가 필요)
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

        // 인증된 사용자 정보 생성 (이메일, 빈 비밀번호, 권한 리스트)
        User principal = new User(email, "", authorities);

        // UsernamePasswordAuthenticationToken을 사용해 인증 객체 반환
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
