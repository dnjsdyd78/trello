package com.sparta.trelloproject.domain.list.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.board.repository.BoardRepository;
import com.sparta.trelloproject.domain.list.dto.request.ListDeleteRequest;
import com.sparta.trelloproject.domain.list.dto.request.ListSaveRequest;
import com.sparta.trelloproject.domain.list.dto.request.ListUpdateRequest;
import com.sparta.trelloproject.domain.list.dto.response.ListSaveResponse;
import com.sparta.trelloproject.domain.list.entity.ListEntity;
import com.sparta.trelloproject.domain.list.repository.ListRepository;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import com.sparta.trelloproject.domain.workspacemember.repository.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Transactional
    public ListSaveResponse saveList(Long boardId, ListSaveRequest request) {
        // 보드 찾기
        Board board = findBoardById(boardId);

        // 현재 사용자에 대한 WorkspaceMember 찾기 (userId와 workspaceId 기반)
        WorkspaceMember member = workspaceMemberRepository.findByUserIdAndWorkspaceId(
                        request.getUserId(), board.getWorkspace().getId()) // workspaceId로 찾기
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));

        // 권한 검사
        checkPermission(member, member.getZrole());

        // 빌더 패턴을 사용하여 ListEntity 객체 생성
        ListEntity newListEntity = ListEntity.builder()
                .title(request.getTitle())
                .sequence(request.getSequence())
                .board(board)
                .build();

        // 엔티티 저장
        listRepository.save(newListEntity);
        return ListSaveResponse.of(newListEntity);
    }

    @Transactional
    public ListSaveResponse updateList(Long listId, ListUpdateRequest request) {
        // 리스트 찾기
        ListEntity existingListEntity = findListById(listId);

        // 보드에서 워크스페이스 ID 가져오기
        Long workspaceId = existingListEntity.getBoard().getWorkspace().getId();

        // 현재 사용자에 대한 WorkspaceMember 찾기 (userId와 workspaceId 기반)
        WorkspaceMember member = workspaceMemberRepository.findByUserIdAndWorkspaceId(
                        request.getUserId(), workspaceId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));


        // 권한 검사
        checkPermission(member, member.getZrole());

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
    public void deleteList(ListDeleteRequest request) {
        // 리스트 찾기
        ListEntity listEntity = listRepository.findById(request.getListId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_ListEntity));

        // 보드에서 워크스페이스 ID 가져오기
        Long workspaceId = listEntity.getBoard().getWorkspace().getId();

        // 현재 사용자에 대한 WorkspaceMember 찾기 (userId와 workspaceId 기반)
        WorkspaceMember member = workspaceMemberRepository.findByUserIdAndWorkspaceId(
                        request.getUserId(), workspaceId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));

        // 권한 검사
        checkPermission(member, member.getZrole());

        // 리스트 삭제
        listRepository.delete(listEntity);
    }


    private ListEntity findListById(Long listId) {
        return listRepository.findById(listId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_ListEntity));
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_BOARD));
    }

    // 권한 체크 메서드
    public void checkPermission(WorkspaceMember member, WorkspaceMember.ZRole requiredRole) {
        if (member.getZrole() == WorkspaceMember.ZRole.READ_ONLY) {
            throw new ApiException(ErrorStatus._FORBIDDEN);
        }
    }
}
