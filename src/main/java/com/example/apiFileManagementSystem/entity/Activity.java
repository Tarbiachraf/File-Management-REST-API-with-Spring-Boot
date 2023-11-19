package com.example.apiFileManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser appuser;

    @Enumerated(EnumType.ORDINAL)
    private ActivityType activityType;
//other attributes
    private LocalDateTime activityDate;

    @ManyToOne
    private GroupEntity group;

    @ManyToOne
    private AppFile appFile;

    @ManyToOne
    private Folder folder;

    @ManyToOne
    private Tag tag;

    @ManyToOne
    private Partage partage;
}
