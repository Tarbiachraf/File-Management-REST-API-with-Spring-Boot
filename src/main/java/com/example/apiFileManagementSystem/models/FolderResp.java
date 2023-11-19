package com.example.apiFileManagementSystem.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FolderResp {
    private Long id;
    private String name;
    private String parentName;
    private String owner;
    private String dateCreation;
}
