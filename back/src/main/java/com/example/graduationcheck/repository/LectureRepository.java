package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findByCourseName(String courseName);
}
