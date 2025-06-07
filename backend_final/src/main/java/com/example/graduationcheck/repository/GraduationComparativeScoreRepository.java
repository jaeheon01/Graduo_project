package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.GraduationComparativeScore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Entity: GraduationComparativeScore(id PK, FK→GraduationRequirementBase.id)
 */
public interface GraduationComparativeScoreRepository extends JpaRepository<GraduationComparativeScore, Integer> {
    // 기본 CRUD만으로 충분합니다.
}
