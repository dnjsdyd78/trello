package com.sparta.trelloproject.domain.workspacemember.dto.response;

import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {

    private Long id;
    private String userName;
    private WorkspaceMember.Role role;

    public MemberResponse(WorkspaceMember member) {
        this.id = member.getId();
        this.userName = member.getUser().getName();  // User 엔티티에서 이름을 가져오는 예시
        this.role = member.getRole();
    }
}