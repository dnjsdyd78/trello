package com.sparta.trelloproject.domain.workspace.exception;

public class WorkspaceNotFoundException extends RuntimeException {
    public WorkspaceNotFoundException(Long workspaceId) {
        super("워크스페이스를 찾을 수 없습니다: " + workspaceId);
    }
}