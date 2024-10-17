package com.sparta.trelloproject.domain.file.controller;

import com.sparta.trelloproject.common.apipayload.dto.ReasonDto;
import com.sparta.trelloproject.domain.file.dto.AttachedFileDto;
import com.sparta.trelloproject.domain.file.service.AttachedFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class AttachedFileController {

    private AttachedFileService attachedFileService;

    @PostMapping
    public ResponseEntity<ReasonDto> addAttachedFile(@RequestBody AttachedFileDto attachedFileDto, @RequestParam boolean isReadOnlyMember) {
        return attachedFileService.saveAttachedFile(attachedFileDto, isReadOnlyMember);
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<AttachedFileDto>> getAttachedFilesByCardId(@PathVariable Long cardId) {
        List<AttachedFileDto> attachedFiles = attachedFileService.getAllAttachedFiles(cardId);
        return ResponseEntity.ok(attachedFiles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReasonDto> deleteAttachedFile(@PathVariable Long id, @RequestParam boolean isReadOnlyMember) {
        return attachedFileService.deleteAttachedFile(id, isReadOnlyMember);
    }
}