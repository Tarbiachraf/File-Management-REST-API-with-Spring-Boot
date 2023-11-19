package com.example.apiFileManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private List<AppFile> childrenFiles;

    @ManyToOne
    private Folder parent;

    @OneToMany(mappedBy = "parent")
    private List<Folder> childrenFolder;

    @ManyToOne
    private AppUser user;

    @OneToMany(mappedBy = "folder")
    private List<Activity> activities;


    // Attribut pour stocker la date de création du dossier
   // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreation;

    // Attribut pour stocker la date de dernière modification du dossier
    private LocalDateTime dateModification;

    @Override
    public String toString() {
        return "";
    }
    // Autres attributs spécifiqu
}


















