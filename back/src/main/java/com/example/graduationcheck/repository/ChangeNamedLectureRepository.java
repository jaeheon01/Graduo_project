package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.ChangeNamedLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangeNamedLectureRepository extends JpaRepository<ChangeNamedLecture, Long> {
    Optional<ChangeNamedLecture> findByPrevLectureName(String prevLectureName);

    default String findCurrentNameIfChanged(String prevName) {
        return findByPrevLectureName(prevName)
                .map(ChangeNamedLecture::getCurrentLectureName)
                .orElse(prevName);
    }
}
