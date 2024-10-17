package com.sparta.trelloproject.domain.workspacemember.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long memberId) {
        super("ID가 " + memberId + " 인 멤버를 찾을 수 없습니다.");
    }
}