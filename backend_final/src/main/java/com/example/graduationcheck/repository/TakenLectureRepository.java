package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.TakenLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Entity: TakenLecture(id PK)
 */
public interface TakenLectureRepository extends JpaRepository<TakenLecture, Integer> {

    // user_id로 TakenLecture 목록 조회
    List<TakenLecture> findByUserId(Integer userId);

    // user_id로 전체 삭제 
    @Modifying
    @Transactional
    void deleteByUserId(Integer userId);
}
