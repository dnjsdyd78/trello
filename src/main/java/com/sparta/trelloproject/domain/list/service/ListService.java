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
    public ListSaveResponse saveList(AuthUser authUser, Long boardId, ListSaveRequest listSaveRequest) {
        User user = User.fromAuthUser(authUser);

        Board board = findBoardById(boardId);

        // 빌더 패턴을 사용하여 ListEntity 객체 생성
        ListEntity newListEntity = ListEntity.builder()
                .title(listSaveRequest.getTitle())
                .sequence(listSaveRequest.getSequence())
                .board(board)
                .build();

        return ListSaveResponse.of(newListEntity);
    }

    @Transactional
    public ListSaveResponse updateList(AuthUser authUser, Long listId, ListUpdateRequest request) {
        User user = User.fromAuthUser(authUser);

        // 리스트 찾기
        ListEntity existingListEntity = findListById(listId);

        // 업데이트할 필드 설정
        // request에서 받은 값으로 리스트의 필드를 업데이트
        // Builder를 사용하여 업데이트할 새로운 ListEntity 객체 생성
        ListEntity updatedListEntity = ListEntity.builder()
                .title(request.getTitle() != null ? request.getTitle() : existingListEntity.getTitle())
                .sequence(request.getSequence() != null ? request.getSequence() : existingListEntity.getSequence())
                .board(existingListEntity.getBoard()) // board는 변경하지 않음
                .build();

        // 리스트를 저장 (변경 사항이 있다면)
        listRepository.save(updatedListEntity);

        return ListSaveResponse.of(updatedListEntity);
    }

    @Transactional
    public ListSaveResponse updateSequenceList(AuthUser authUser, Long listId, ListSequenceUpdateRequest request) {
        // 사용자 정보 가져오기 (필요에 따라 사용)
        User user = User.fromAuthUser(authUser);

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
    public void deleteList(AuthUser authUser, Long listId) {
        User user = User.fromAuthUser(authUser);
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
