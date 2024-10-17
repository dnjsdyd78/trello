package com.sparta.trelloproject.domain.manager.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.manager.dto.request.ManagerRequest;
import com.sparta.trelloproject.domain.manager.dto.response.ManagerResponse;
import com.sparta.trelloproject.domain.manager.entity.Manager;
import com.sparta.trelloproject.domain.manager.repository.ManagerRepository;
import com.sparta.trelloproject.domain.user.entity.User;
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

    // 매니저 생성
    @Transactional
    public ManagerResponse saveManager(Long cardId, ManagerRequest request) {
        // 현재 사용자에 대한 WorkspaceMember 찾기
        WorkspaceMember member = workSpaceMemberRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));

        // 권한 검사
        checkPermission(member);

        Card card = findCardById(cardId);
        WorkspaceMember workspaceMember = findWorkspaceMemberById(request.getWorkSpaceMemberId()); // workSpaceMemberId 추가

        Manager manager = Manager.builder()
                .card(card)
                .workSpaceMember(workspaceMember)
                .email(workspaceMember.getUser().getEmail())
                .build();

        managerRepository.save(manager);
        return new ManagerResponse(manager);
    }

    @Transactional
    public void deleteManager(ManagerRequest request) {
        // 현재 사용자에 대한 WorkspaceMember 찾기
        WorkspaceMember member = workSpaceMemberRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));

        // 권한 검사
        checkPermission(member);
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
        if (member.getRole() == WorkspaceMember.Role.READ_ONLY) {
            throw new ApiException(ErrorStatus._FORBIDDEN);
        }
    }
}