package com.sparta.trelloproject.domain.workspace.dto.response;

import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import lombok.Getter;

@Getter
public class WorkspaceResponse {
    private Long id;
    private String name;
    private String description;

    // 생성자
    public WorkspaceResponse(Workspace workspace) {
        this.id = workspace.getId();
        this.name = workspace.getName();
        this.description = workspace.getDescription();
    }
}
