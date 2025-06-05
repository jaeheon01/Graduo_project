package com.example.graduationcheck.service;

import com.example.graduationcheck.model.TranscriptCourse;
import com.example.graduationcheck.model.ChangeNamedLecture;
import com.example.graduationcheck.repository.ChangeNamedLectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RetakeRecommendationService {
    private final ChangeNamedLectureRepository changeNamedLectureRepository;

    public List<String> getRetakeRecommendations(List<TranscriptCourse> courses) {
        List<String> recommendations = new ArrayList<>();
        Set<String> lowGrades = Set.of("C+", "C0", "F");

        for (TranscriptCourse course : courses) {
            if (lowGrades.contains(course.getGrade())) {
                String courseName = course.getCourseName();
                Optional<ChangeNamedLecture> changed = changeNamedLectureRepository.findByPrevLectureName(courseName);
                recommendations.add(changed.map(ChangeNamedLecture::getCurrentLectureName).orElse(courseName));
            }
        }
        return recommendations;
    }
}
