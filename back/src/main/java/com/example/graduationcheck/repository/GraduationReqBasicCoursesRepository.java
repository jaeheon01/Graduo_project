package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.GraduationReqBasicCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GraduationReqBasicCoursesRepository extends JpaRepository<GraduationReqBasicCourse, Long> {
    @Query("SELECT g.lectureId FROM GraduationReqBasicCourse g WHERE g.major = :major")
    List<Long> findLectureIdsByMajor(String major);
}