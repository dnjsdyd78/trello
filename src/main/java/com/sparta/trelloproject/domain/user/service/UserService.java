package com.sparta.trelloproject.domain.user.service;

import com.sparta.trelloproject.domain.user.dto.DeleteRequestDto;
import com.sparta.trelloproject.domain.user.dto.LoginRequestDto;
import com.sparta.trelloproject.domain.user.dto.UserRequestDto;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import com.sparta.trelloproject.jwt.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입 처리
    public void register(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        if (!isValidPassword(userRequestDto.getPassword())) {
            throw new IllegalArgumentException("Password does not meet the requirements.");
        }
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto.getEmail(), encodedPassword, userRequestDto.getRole());
        userRepository.save(user);
    }

    // 로그인 처리 및 JWT 생성
    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    // 회원 탈퇴 처리
    public void deleteAccount(DeleteRequestDto deleteRequestDto) {
        User user = userRepository.findByEmail(deleteRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }
        userRepository.delete(user);
    }

    // 비밀번호 유효성 검사
    private boolean isValidPassword(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[!@#$%^&*].*");
    }
}
