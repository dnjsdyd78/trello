package com.sparta.trelloproject.domain.workspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WorkspaceUpdateRequest {
    @NotBlank(message = "Workspace 이름은 필수입니다.")
    private String name;
    private String description;
}