package com.example.graduationcheck.service;

import com.example.graduationcheck.dto.GraduationResult;
import com.example.graduationcheck.model.*;
import com.example.graduationcheck.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GraduationCheckService {

    private final UserRepository userRepository;
    private final TranscriptRepository transcriptRepository;
    private final MajorRepository majorRepository;
    private final GraduationRequirementBaseRepository baseRepository;
    private final ExternalScoreRepository externalScoreRepository;
    private final GraduationComparativeScoreRepository comparativeScoreRepository;
    private final RetakeRecommendationService retakeRecommendationService;

    public GraduationResult checkGraduation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<TranscriptCourse> courses = transcriptRepository.findByUserId(userId);

        int totalCredits = courses.stream().mapToInt(TranscriptCourse::getCredit).sum();

        GraduationRequirementBase base = baseRepository.findByDepartmentAndStartYearLessThanEqualAndEndYearGreaterThanEqual(
                        user.getDepartment(), user.getEntranceYear(), user.getEntranceYear())
                .orElseThrow();

        boolean totalCreditOk = totalCredits >= base.getTotalCredit();

        List<Major> majors = majorRepository.findByDepartment(user.getDepartment());
        int majorCredits = courses.stream()
                .filter(course -> majors.stream().anyMatch(m -> m.getCourseName().equals(course.getCourseName())))
                .mapToInt(TranscriptCourse::getCredit)
                .sum();

        boolean majorCreditOk = majorCredits >= 60;

        List<Major> requiredMajors = majorRepository.findByDepartmentAndIsRequiredTrue(user.getDepartment());
        Set<String> takenCourseNames = courses.stream().map(TranscriptCourse::getCourseName).collect(Collectors.toSet());
        boolean requiredMajorCoursesOk = requiredMajors.stream()
                .allMatch(m -> takenCourseNames.contains(m.getCourseName()));

        boolean liberalAreaOk = checkLiberalArea(base, courses);

        boolean requiredGeneralCoursesOk = true; // 생략된 구현

        boolean externalLanguageOk = checkExternalScore(userId, "toeic");
        boolean externalProgrammingOk = checkExternalScore(userId, "apc");

        List<String> retakeRecommendations = retakeRecommendationService.getRetakeRecommendations(courses);

        return GraduationResult.builder()
                .totalCreditOk(totalCreditOk)
                .majorCreditOk(majorCreditOk)
                .requiredMajorCoursesOk(requiredMajorCoursesOk)
                .requiredGeneralCoursesOk(requiredGeneralCoursesOk)
                .liberalAreaOk(liberalAreaOk)
                .externalLanguageOk(externalLanguageOk)
                .externalProgrammingOk(externalProgrammingOk)
                .retakeRecommendations(retakeRecommendations)
                .missingCourses(List.of())
                .build();
    }

    private boolean checkLiberalArea(GraduationRequirementBase base, List<TranscriptCourse> courses) {
        Set<String> requiredFields = base.getDistributionAreaCredit() == 12 ?
                Set.of("역사와 철학", "문학과 예술", "인간과 사회", "연결과 통합") :
                Set.of("역사와 철학", "문학과 예술", "인간과 사회");

        Set<String> takenFields = courses.stream()
                .map(TranscriptCourse::getSubjectField)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return takenFields.containsAll(requiredFields);
    }

    private boolean checkExternalScore(Long userId, String scoreType) {
        return externalScoreRepository.findByUserIdAndType(userId, scoreType)
                .map(score -> comparativeScoreRepository.findByScoreType(scoreType)
                        .map(c -> score.getScore() >= c.getRequiredScore())
                        .orElse(false))
                .orElse(false);
    }
}
