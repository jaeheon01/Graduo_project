package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.ExternalScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExternalScoreRepository extends JpaRepository<ExternalScore, Long> {
    Optional<ExternalScore> findByUserIdAndType(Long userId, String externalScoreType);
}
