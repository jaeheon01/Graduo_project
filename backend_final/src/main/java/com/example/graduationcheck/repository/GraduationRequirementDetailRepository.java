package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.GraduationRequirementDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Entity: GraduationRequirementDetail(id PK)
 */
public interface GraduationRequirementDetailRepository extends JpaRepository<GraduationRequirementDetail, Integer> {

    // base_id, is_dual_degree 조합으로 단일 행 조회
    GraduationRequirementDetail findByBaseIdAndIsDualDegree(Integer baseId, Boolean isDualDegree);
}
