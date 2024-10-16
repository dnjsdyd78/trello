package com.sparta.trelloproject.domain.workspace.exception;

public class WorkspaceNotFoundException extends RuntimeException {
    public WorkspaceNotFoundException(Long workspaceId) {
        super("Workspace not found with id: " + workspaceId);
    }
}