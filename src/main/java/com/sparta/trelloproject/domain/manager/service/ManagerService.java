package com.sparta.trelloproject.domain.manager.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.manager.dto.request.ManagerRequest;
import com.sparta.trelloproject.domain.manager.dto.response.ManagerResponse;
import com.sparta.trelloproject.domain.manager.entity.Manager;
import com.sparta.trelloproject.domain.manager.repository.ManagerRepository;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import com.sparta.trelloproject.domain.workspacemember.repository.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final CardRepository cardRepository;
    private final WorkspaceMemberRepository workSpaceMemberRepository;

    @Transactional
    public ManagerResponse saveManager(Long cardId, ManagerRequest request) {
        // 카드 먼저 찾기
        Card card = findCardById(cardId);

        // 카드의 리스트를 통해 워크스페이스 ID 가져오기
        Long workspaceId = card.getListEntity().getBoard().getWorkspace().getId();

        // 현재 사용자에 대한 WorkspaceMember 찾기 (userId와 workspaceId 기반)
        WorkspaceMember member = workSpaceMemberRepository.findByUserIdAndWorkspaceId(
                        request.getUserId(), workspaceId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));

        // 권한 검사
        checkPermission(member);

        // 요청에서 제공된 workSpaceMemberId로 해당 WorkspaceMember 찾기
        WorkspaceMember workspaceMember = findWorkspaceMemberById(request.getWorkSpaceMemberId());

        // Manager 객체 생성
        Manager manager = Manager.builder()
                .card(card)
                .workSpaceMember(workspaceMember)
                .email(workspaceMember.getUser().getEmail())
                .build();

        // Manager 객체 저장
        managerRepository.save(manager);

        // 응답 생성
        return new ManagerResponse(manager);
    }

    @Transactional
    public void deleteManager(ManagerRequest request) {
        // 매니저 먼저 찾기
        Manager manager = managerRepository.findById(request.getManagerId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_MANAGER));

        // 매니저가 속한 카드 -> 리스트 -> 보드를 통해 워크스페이스 ID 가져오기
        Long workspaceId = manager.getCard().getListEntity().getBoard().getWorkspace().getId();

        // 현재 사용자에 대한 WorkspaceMember 찾기 (userId와 workspaceId 기반)
        WorkspaceMember member = workSpaceMemberRepository.findByUserIdAndWorkspaceId(
                        request.getUserId(), workspaceId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));

        // 권한 검사
        checkPermission(member);

        // 매니저 삭제
        managerRepository.deleteById(request.getManagerId());
    }


    // 특정 카드 찾기
    private Card findCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));
    }

    // 특정 워크스페이스 멤버 찾기
    private WorkspaceMember findWorkspaceMemberById(Long workspaceMemberId) {
        return workSpaceMemberRepository.findById(workspaceMemberId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));
    }

    // 권한 체크 메서드
    public void checkPermission(WorkspaceMember member) {
        if (member.getZrole() == WorkspaceMember.ZRole.READ_ONLY) {
            throw new ApiException(ErrorStatus._FORBIDDEN);
        }
    }
}