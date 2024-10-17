package com.sparta.trelloproject.domain.comment.entity;

import com.sparta.trelloproject.common.entity.Timestamped;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment", nullable = false, length = 255)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(String contents, Card card, User user) {
        this.contents = contents;
        this.card = card;
        this.user = user;
    }

    public void update(String contents) {
        this.contents = contents;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getComment() {
        return contents;
    }
}
