package com.example.apiFileManagementSystem.repos;

import com.example.apiFileManagementSystem.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {
    public Optional<GroupEntity> findByGroupNom(String nom);
}
