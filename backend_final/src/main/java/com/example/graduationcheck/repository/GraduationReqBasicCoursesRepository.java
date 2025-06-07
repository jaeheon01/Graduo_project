package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.GraduationReqBasicCourses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Entity: GraduationReqBasicCourses(id PK)
 */
public interface GraduationReqBasicCoursesRepository extends JpaRepository<GraduationReqBasicCourses, Integer> {

    // base_id 로 기초 교양과목 목록 조회
    List<GraduationReqBasicCourses> findByBaseId(Integer baseId);
}
