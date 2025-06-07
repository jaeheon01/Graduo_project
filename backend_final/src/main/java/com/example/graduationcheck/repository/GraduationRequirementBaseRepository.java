package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.GraduationRequirementBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Entity: GraduationRequirementBase(id PK)
 */
public interface GraduationRequirementBaseRepository extends JpaRepository<GraduationRequirementBase, Integer> {

    /**
     * department가 일치하고, start_year ≤ year1, end_year ≥ year2인 기준 행 조회
     */
    GraduationRequirementBase findByDepartmentAndStartYearLessThanEqualAndEndYearGreaterThanEqual(
        String department, Integer year1, Integer year2);
}
