package com.sparta.trelloproject.domain.file.entity;

import com.sparta.trelloproject.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.trelloproject.domain.card.entity.Card;

@Getter
@Entity
@NoArgsConstructor
public class AttachedFile extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    public AttachedFile(String fileName, String fileType, int size, Card card, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = size;
        this.card = card;
        this.filePath = filePath;
    }
}
