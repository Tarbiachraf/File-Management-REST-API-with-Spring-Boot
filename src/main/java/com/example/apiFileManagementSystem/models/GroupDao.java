package com.example.apiFileManagementSystem.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDao {
     private Long id;
     private String groupNom;
}
