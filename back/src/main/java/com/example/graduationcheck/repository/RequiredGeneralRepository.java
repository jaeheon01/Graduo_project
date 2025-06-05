package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.RequiredGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequiredGeneralRepository extends JpaRepository<RequiredGeneral, Long> {
    @Query("SELECT r.lectureId FROM RequiredGeneral r")
    List<Long> findAllLectureIds();
}
