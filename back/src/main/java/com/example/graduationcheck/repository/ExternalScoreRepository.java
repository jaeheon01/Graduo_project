package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.ExternalScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExternalScoreRepository extends JpaRepository<ExternalScore, Long> {
    @Query("SELECT e.score FROM ExternalScore e WHERE e.userId = :userId")
    Integer findScoreByUserId(Long userId);
}
