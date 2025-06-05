package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByDepartment(String department);
    List<Major> findByDepartmentAndIsRequiredTrue(String department);
    List<Major> findByDepartmentAndIsIndustryTrue(String department);
}
