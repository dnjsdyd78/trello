package com.sparta.trelloproject.domain.manager.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ManagerSaveRequest {
    private Long cardId;  // 카드 ID
    private Long workSpaceMemberId;  // 워크스페이스 멤버 ID
}
