package com.sparta.trelloproject.domain.card.entity;

import com.sparta.trelloproject.common.entity.Timestamped;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.comment.entity.Comment;
import com.sparta.trelloproject.domain.list.entity.ListEntity;
import com.sparta.trelloproject.domain.manager.entity.Manager;
import com.sparta.trelloproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table
public class Card extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime deadLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", nullable = false)
    private ListEntity listEntity;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Manager> managers;

    @Builder
    public Card(String title, String content, LocalDateTime deadLine, ListEntity listEntity, User user) {
        this.title = title;
        this.content = content;
        this.deadLine = deadLine;
        this.listEntity = listEntity;
        this.user = user;
    }

    // Factory method to create a Card from CardSaveRequest
    public static Card from(CardSaveRequest cardSaveRequest, ListEntity listEntity, User user) {
        return Card.builder()
                .title(cardSaveRequest.getTitle())
                .content(cardSaveRequest.getContent())
                .deadLine(cardSaveRequest.getDeadLine())
                .listEntity(listEntity)
                .user(user)
                .build();
    }
}
