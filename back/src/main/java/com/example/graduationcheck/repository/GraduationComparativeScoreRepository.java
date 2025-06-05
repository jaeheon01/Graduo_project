package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.GraduationComparativeScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GraduationComparativeScoreRepository extends JpaRepository<GraduationComparativeScore, Long> {
    Optional<GraduationComparativeScore> findByScoreType(String scoreType);
}
