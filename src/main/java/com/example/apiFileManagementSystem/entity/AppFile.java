package com.example.apiFileManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fileType;
    private Long size;

    @ManyToOne
    private Folder dossier;

    @ManyToMany
    private Set<Tag> tags;

    @OneToMany(mappedBy = "appFile")
    private List<Activity> activities;

    @OneToMany(mappedBy = "file")
    private List<Partage> partageList;


    @Override
    public String toString() {
        return "";
    }


}
