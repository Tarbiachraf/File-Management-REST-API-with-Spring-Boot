package com.example.apiFileManagementSystem.repos;

import com.example.apiFileManagementSystem.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder,Long> {

    Optional<Folder>  findByNom(String nom);
}
