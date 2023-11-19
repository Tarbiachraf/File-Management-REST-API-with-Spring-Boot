package com.example.apiFileManagementSystem.models;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class FolderRequest {
    private String name;
    @Nullable
    private Long parentId;
}
