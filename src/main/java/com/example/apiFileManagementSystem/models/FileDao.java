package com.example.apiFileManagementSystem.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDao {
    private String name;
    private String fileType;
    private Long size;
    private String owner;
    private String folder;
}
