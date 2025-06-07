package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Entity: Lecture(lecture_id PK)
 * 
 * NOTE: isRequired, isIndustry 필드는 Lecture에 없으므로 여기서 제거했습니다.
 * 전공필수 조회는 Major 테이블을 통해 수행합니다 (MajorRepository 참고).
 */
public interface LectureRepository extends JpaRepository<Lecture, String> {

    // 과목명(name)으로 강좌 목록 조회
    List<Lecture> findByName(String name);
}
