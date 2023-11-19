package com.example.apiFileManagementSystem.repos;

import com.example.apiFileManagementSystem.entity.Partage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartageRepository extends JpaRepository<Partage,Long> {
    public Optional<Partage> findBySourceUserIdOrTargetUserId(Long id1, Long id2);
}
