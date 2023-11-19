package com.example.apiFileManagementSystem.repos;

import com.example.apiFileManagementSystem.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
