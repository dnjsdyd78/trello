package com.sparta.trelloproject.domain.file.service;

import com.sparta.trelloproject.common.apipayload.dto.ReasonDto;
import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.apipayload.status.SuccessStatus;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.file.entity.AttachedFile;
import com.sparta.trelloproject.domain.file.repository.AttachedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.sparta.trelloproject.domain.file.dto.AttachedFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.Collectors;

import static com.sparta.trelloproject.domain.card.entity.QCard.card;

@Service
@RequiredArgsConstructor
public class AttachedFileService {

    private final AttachedFileRepository attachedFileRepository;

    private static final int MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> SUPPORTED_FILE_TYPES = List.of("jpg", "jpeg", "png", "pdf", "csv");

    @Transactional
    public ResponseEntity<ReasonDto> saveAttachedFile(AttachedFileDto attachedFileDto, boolean isReadOnlyMember) {
        if (isReadOnlyMember) {
            return ResponseEntity
                    .status(ErrorStatus._FORBIDDEN.getHttpStatus())
                    .body(ErrorStatus._FORBIDDEN.getReasonHttpStatus());
        }

        // 파일 크기 검증
        if (attachedFileDto.getFileSize() > MAX_FILE_SIZE) {
            return ResponseEntity
                    .status(ErrorStatus._INVALID_FILE_SIZE.getHttpStatus())
                    .body(ErrorStatus._INVALID_FILE_SIZE.getReasonHttpStatus());
        }

        // 파일 형식 검증
        String fileExtension = attachedFileDto.getFileName()
                .substring(attachedFileDto.getFileName().lastIndexOf('.') + 1).toLowerCase();
        if (!SUPPORTED_FILE_TYPES.contains(fileExtension)) {
            return ResponseEntity
                    .status(ErrorStatus._INVALID_FILE_TYPE.getHttpStatus())
                    .body(ErrorStatus._INVALID_FILE_TYPE.getReasonHttpStatus());
        }

        // 정상 처리 로직
        AttachedFile attachedFile = new AttachedFile(
                attachedFileDto.getFileName(),
                attachedFileDto.getFileType(),
                attachedFileDto.getFileSize(),
                attachedFileDto.getCard(), // CardEntity를 가져오는 방식에 맞게 수정
                attachedFileDto.getFilePath()
        );

        attachedFileRepository.save(attachedFile);
        return ResponseEntity.ok(SuccessStatus._Ok.getReasonHttpStatus());
    }

    @Transactional
    public ResponseEntity<ReasonDto> deleteAttachedFile(Long id, boolean isReadOnlyMember) {
        if (isReadOnlyMember) {
            return ResponseEntity
                    .status(ErrorStatus._FORBIDDEN.getHttpStatus())
                    .body(ErrorStatus._FORBIDDEN.getReasonHttpStatus());
        }

        AttachedFile attachedFile = attachedFileRepository.findById(id)
                .orElse(null); // 파일이 없으면 null 반환

        if (attachedFile == null) {
            return ResponseEntity
                    .status(ErrorStatus._NOT_FOUND_CARD.getHttpStatus())
                    .body(ErrorStatus._NOT_FOUND_CARD.getReasonHttpStatus());
        }

        // 파일 삭제
        attachedFileRepository.delete(attachedFile);

        // 삭제 성공 시 응답
        return ResponseEntity.ok(SuccessStatus._Ok.getReasonHttpStatus());
    }

    public List<AttachedFileDto> getAllAttachedFiles(Long cardId) {
        return attachedFileRepository.findByCardId(cardId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private AttachedFileDto convertToDto(AttachedFile attachedFile) {
        return new AttachedFileDto(
                attachedFile.getFileName(),
                attachedFile.getFileType(),
                attachedFile.getFileSize(),
                attachedFile.getFilePath(),
                attachedFile.getCard() // 필요에 따라 카드 정보 추가
        );
    }
}