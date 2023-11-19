package com.example.apiFileManagementSystem.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartageDao {
    private String sourceUser;
    private String targetUser;
    private String targetGroup;
    private String fileName;
}
