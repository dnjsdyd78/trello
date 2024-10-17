package com.sparta.trelloproject.domain.manager.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.manager.dto.response.ManagerResponse;
import com.sparta.trelloproject.domain.manager.entity.Manager;
import com.sparta.trelloproject.domain.manager.repository.ManagerRepository;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.workspacemember.entity.WorkspaceMember;
import com.sparta.trelloproject.domain.workspacemember.repository.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository; // 매니저 리포지토리
    private final CardRepository cardRepository; // 카드 리포지토리
    private final WorkspaceMemberRepository workSpaceMemberRepository; // 워크스페이스 멤버 리포지토리

    // 매니저 생성
    @Transactional
    public ManagerResponse saveManager(AuthUser authUser, Long cardId, Long workspaceMemberId) {
        User user = User.fromAuthUser(authUser);

        Card card = findCardById(cardId);

        WorkspaceMember workSpaceMember = findWorkSpaceMemberById(workspaceMemberId);

        Manager manager = Manager.builder()
                .card(card)
                .workSpaceMember(workSpaceMember)
                .build();

        // 매니저 저장
        managerRepository.save(manager);
        return new ManagerResponse(manager); // ManagerResponse 반환
    }

    // 매니저 삭제
//    @Transactional
//    public void deleteManager(AuthUser authUser, Long managerId) {
//        managerRepository.deleteById(managerId);
//    }
//
//
//
//    // 카드에 연관된 매니저 조회
//    public List<ManagerResponse> getManagersByCardId(Long managerId) {
//        List<Manager> managers = managerRepository.findById(managerId);
//        return convertManagersToResponse(managers); // 매니저 리스트를 DTO로 변환
//    }

    // 특정 카드 찾기
    private Card findCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));
    }

    // 특정 워크스페이스 멤버 찾기
    private WorkspaceMember findWorkSpaceMemberById(Long workspaceMemberId) {
        return workSpaceMemberRepository.findById(workspaceMemberId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE_MEMBER));
    }

    // 매니저 리스트를 DTO로 변환하는 메서드
    private List<ManagerResponse> convertManagersToResponse(List<Manager> managers) {
        return managers.stream()
                .map(ManagerResponse::new) // ManagerResponse 생성
                .collect(Collectors.toList());
    }
}