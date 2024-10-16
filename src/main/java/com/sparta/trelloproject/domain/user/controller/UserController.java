package com.sparta.trelloproject.domain.user.controller;

import com.sparta.trelloproject.domain.user.dto.DeleteRequestDto;
import com.sparta.trelloproject.domain.user.dto.LoginRequestDto;
import com.sparta.trelloproject.domain.user.dto.UserRequestDto;
import com.sparta.trelloproject.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDto userRequestDto) {
        userService.register(userRequestDto);
        return ResponseEntity.ok("User registered successfully.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = userService.login(loginRequestDto);
        return ResponseEntity.ok(token);

    }

    // 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteRequestDto deleteRequestDto) {
        userService.deleteAccount(deleteRequestDto);
        return ResponseEntity.ok("Account deleted successfully.");
    }
}
