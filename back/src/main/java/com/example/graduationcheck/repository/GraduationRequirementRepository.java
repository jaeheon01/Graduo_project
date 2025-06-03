package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.GraduationRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraduationRequirementRepository extends JpaRepository<GraduationRequirement, Long> {}
