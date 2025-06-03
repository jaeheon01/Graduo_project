package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.TranscriptCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranscriptRepository extends JpaRepository<TranscriptCourse, Long> {
    List<TranscriptCourse> findByUserId(Long userId);
}
