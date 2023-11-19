package com.example.apiFileManagementSystem.repos;

import com.example.apiFileManagementSystem.entity.AppFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<AppFile,Long> {
    Optional<AppFile> findByName(String name);
}
