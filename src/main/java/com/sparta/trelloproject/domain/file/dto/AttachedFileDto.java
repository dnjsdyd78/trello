package com.sparta.trelloproject.domain.file.dto;

import com.sparta.trelloproject.domain.card.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachedFileDto {

    private String fileName;
    private String fileType;
    private Integer fileSize;
    private String filePath;
    private Card card; // Card의 ID
}