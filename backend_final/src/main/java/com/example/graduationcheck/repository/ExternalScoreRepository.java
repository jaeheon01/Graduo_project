package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.ExternalScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Entity: ExternalScore(external_score_id PK)
 */
public interface ExternalScoreRepository extends JpaRepository<ExternalScore, Integer> {

    // user_id로 외부 점수 목록 조회
    List<ExternalScore> findByUserId(Integer userId);
}
