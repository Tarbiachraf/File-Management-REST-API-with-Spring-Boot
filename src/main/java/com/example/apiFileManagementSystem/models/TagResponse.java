package com.example.apiFileManagementSystem.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TagResponse {
    private Long id;
    private String tagNom;
}
