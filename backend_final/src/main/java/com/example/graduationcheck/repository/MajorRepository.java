package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, String> {

    // 기존: 전공필수 과목 조회
    List<Major> findByIsRequiredTrue();

    // 새로 추가: 산학협력 과목(is_industry=true) 전체 조회
    List<Major> findByIsIndustryTrue();
}
