package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.RequiredGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Entity: RequiredGeneral(lecture_id PK)
 */
public interface RequiredGeneralRepository extends JpaRepository<RequiredGeneral, String> {
    // 기본 CRUD (findById 등)만으로 충분합니다.
}
