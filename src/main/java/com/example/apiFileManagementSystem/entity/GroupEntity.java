package com.example.apiFileManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupNom;

    @ManyToMany(mappedBy = "groups")
    private Set<AppUser> appUsers;

    @OneToMany(mappedBy = "group")
    private List<Activity> activities;

    @OneToMany(mappedBy = "targetGroup")
    private List<Partage> partageList;
}
