package com.example.apiFileManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 45)
    private String email;
    @Column(nullable = false, length = 65)
    private String password;
    @Column(nullable = false, length = 20)
    private String firstName;
    @Column(nullable = false, length = 20)
    private String lastName;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Folder> folders;
   /* @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles;*/
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @ManyToMany
    private Set<GroupEntity> groups;

    @OneToMany(mappedBy = "sourceUser")
    @JsonManagedReference
    private List<Partage> sharedWithUsers;

    @OneToMany(mappedBy = "targetUser")
    @JsonManagedReference
    private List<Partage> sharedByUsers;

    @OneToMany(mappedBy = "appuser")
    private List<Activity> activities;

    @Override
    public String toString() {
        return "";
    }
}
