package com.sparta.trelloproject.domain.manager.entity;

import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Manager {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_member_id")
    private WorkspaceMember workSpaceMember;

    @Column(nullable = false)
    private String email; // 이메일 필드 추가

    // 매니저 생성자
    @Builder
    public Manager(Card card, WorkspaceMember workSpaceMember) {
        this.card = card;
        this.workSpaceMember = workSpaceMember;
        this.email = workSpaceMember.getUser().getEmail(); // 유저의 이메일을 매니저의 이메일로 설정
    }
}
