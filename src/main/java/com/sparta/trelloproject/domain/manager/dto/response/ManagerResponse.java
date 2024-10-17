package com.sparta.trelloproject.domain.manager.dto.response;

import com.sparta.trelloproject.domain.manager.entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManagerResponse {
    private Long managerId;
    private Long cardId; // 카드 ID
    private Long workspaceMemberId; // 워크스페이스 멤버 ID
    private String email; // 이메일

    // 매니저 정보를 바탕으로 Response 객체 생성
    public ManagerResponse(Manager manager) {
        this.managerId = manager.getManagerId();
        this.cardId = manager.getCard().getCardId(); // 카드 ID 가져오기
        this.workspaceMemberId = manager.getWorkspaceMember().getWorkspaceMemberId(); // 워크스페이스 멤버 ID 가져오기
        this.email = manager.getEmail(); // 이메일
    }
}
