package com.sparta.trelloproject.domain.list.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.board.repository.BoardRepository;
import com.sparta.trelloproject.domain.list.dto.request.ListSequenceUpdateRequest;
import com.sparta.trelloproject.domain.list.dto.request.ListSaveRequest;
import com.sparta.trelloproject.domain.list.dto.request.ListUpdateRequest;
import com.sparta.trelloproject.domain.list.dto.response.ListSaveResponse;
import com.sparta.trelloproject.domain.list.entity.ListEntity;
import com.sparta.trelloproject.domain.list.repository.ListRepository;
import com.sparta.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ListSaveResponse saveList(Long boardId, ListSaveRequest listSaveRequest) {
        Board board = findBoardById(boardId);

        // 빌더 패턴을 사용하여 ListEntity 객체 생성
        ListEntity newListEntity = ListEntity.builder()
                .title(listSaveRequest.getTitle())
                .sequence(listSaveRequest.getSequence())
                .board(board)
                .build();

        // 엔티티 저장 로직 추가
        listRepository.save(newListEntity);
        return ListSaveResponse.of(newListEntity);
    }

    @Transactional
    public ListSaveResponse updateList(Long listId, ListUpdateRequest request) {
        // 리스트 찾기
        ListEntity existingListEntity = findListById(listId);

        // 업데이트할 필드 설정
        if (request.getTitle() != null) {
            existingListEntity.updateTitle(request.getTitle());  // 타이틀 업데이트
        }

        if (request.getSequence() != null) {
            existingListEntity.updateSequence(request.getSequence());  // 시퀀스 업데이트
        }

        // 기존 엔티티는 이미 영속성 컨텍스트에 포함되어 있으므로, 별도의 save 호출 필요 없음
        return ListSaveResponse.of(existingListEntity);
    }

    @Transactional
    public ListSaveResponse updateSequenceList(Long listId, ListSequenceUpdateRequest request) {
        // 리스트 찾기
        ListEntity existingListEntity = findListById(listId);

        // 빌더를 사용하여 업데이트할 새로운 ListEntity 객체 생성
        ListEntity updatedListEntity = ListEntity.builder()
                .title(existingListEntity.getTitle()) // 기존 제목 유지
                .sequence(request.getSequence() != null ? request.getSequence() : existingListEntity.getSequence()) // 새로운 순서로 업데이트
                .board(existingListEntity.getBoard()) // 기존 보드 유지
                .build();

        // 변경된 리스트를 저장 (리포지토리가 필요하다면)
        ListEntity savedListEntity = listRepository.save(updatedListEntity); // 리포지토리가 필요합니다.

        return ListSaveResponse.of(savedListEntity);
    }

    @Transactional
    public void deleteList(Long listId) {
        listRepository.deleteById(listId);
    }


    private ListEntity findListById(Long listId) {
        return listRepository.findById(listId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_ListEntity));
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_BOARD));
    }
}
