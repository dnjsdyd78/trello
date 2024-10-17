package com.sparta.trelloproject.domain.manager.entity;

import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.workSpaceMameber.entity.WorkspaceMember;
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
    private WorkspaceMember workspaceMember;

    @Column(nullable = false)
    private String email; // 이메일 필드 추가

    // 매니저 생성자
    @Builder
    public Manager(Card card, WorkspaceMember workSpaceMember, String email) {
        this.card = card;
        this.workspaceMember = workSpaceMember;
        this.email = email;
    }
}
