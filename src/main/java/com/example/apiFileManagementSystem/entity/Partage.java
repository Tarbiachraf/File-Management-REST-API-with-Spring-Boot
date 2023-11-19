package com.example.apiFileManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser sourceUser; // Utilisateur source (celui qui effectue le partage)

    @ManyToOne
    @JsonBackReference
    private AppUser targetUser; // Utilisateur cible (celui avec qui la ressource est partagée)

    @ManyToOne
    @JsonBackReference
    private GroupEntity targetGroup; // Groupe cible (si le partage est avec un groupe)

    @ManyToOne
    private AppFile file;

    @OneToMany(mappedBy = "partage")
    private List<Activity> activities;

    @Override
    public String toString() {
        return "";
    }
}
