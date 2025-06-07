package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.ChangeNamedLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeNamedLectureRepository extends JpaRepository<ChangeNamedLecture, String> {
    // 기존 메서드 ( prevLectureName → currentLectureName 조회 )
    ChangeNamedLecture findByPrevLectureName(String prevLectureName);

    // 신규 메서드 ( currentLectureName → prevLectureName 조회 )
    ChangeNamedLecture findByCurrentLectureName(String currentLectureName);
}
