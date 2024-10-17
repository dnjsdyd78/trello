package com.sparta.trelloproject.domain.file.repository;

import com.sparta.trelloproject.domain.file.entity.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {
    List<AttachedFile> findByCardId(Long cardId);
}
