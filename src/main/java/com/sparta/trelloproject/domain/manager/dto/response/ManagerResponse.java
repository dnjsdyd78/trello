package com.sparta.trelloproject.domain.manager.dto.response;

import com.sparta.trelloproject.domain.manager.entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManagerResponse {
    private Long managerId; // 매니저 ID
    private String email; // 이메일

    // Manager 객체로부터 ManagerResponse 생성
    public ManagerResponse(Manager manager) {
        this.managerId = manager.getManagerId();
        this.email = manager.getEmail(); // 이메일 정보 설정
    }
}
