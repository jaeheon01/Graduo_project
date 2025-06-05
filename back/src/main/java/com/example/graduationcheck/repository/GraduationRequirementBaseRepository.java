package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.GraduationRequirementBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GraduationRequirementBaseRepository extends JpaRepository<GraduationRequirementBase, Long> {
    Optional<GraduationRequirementBase> findByDepartmentAndStartYearLessThanEqualAndEndYearGreaterThanEqual(String department, int year1, int year2);
}