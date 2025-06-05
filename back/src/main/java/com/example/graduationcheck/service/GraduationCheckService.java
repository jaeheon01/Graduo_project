package com.example.graduationcheck.service;

import com.example.graduationcheck.dto.GraduationResult;
import com.example.graduationcheck.model.TranscriptCourse;
import com.example.graduationcheck.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GraduationCheckService {
    private final TranscriptRepository transcriptRepository;
    private final GraduationReqBasicCoursesRepository reqBasicRepository;
    private final RequiredGeneralRepository requiredGeneralRepository;
    private final ExternalScoreRepository externalScoreRepository;

    public GraduationResult checkGraduation(Long userId) {
        List<TranscriptCourse> courses = transcriptRepository.findByUserId(userId);
        int totalCredits = courses.stream().mapToInt(TranscriptCourse::getCredit).sum();
        List<Long> takenLectureIds = courses.stream()
                .map(TranscriptCourse::getLectureId)
                .filter(Objects::nonNull)
                .toList();

        boolean creditOk = totalCredits >= 130;
        boolean majorRequiredOk = takenLectureIds.containsAll(reqBasicRepository.findLectureIdsByMajor("소프트웨어학과"));
        boolean generalRequiredOk = takenLectureIds.containsAll(requiredGeneralRepository.findAllLectureIds());
        Integer score = externalScoreRepository.findScoreByUserId(userId);
        boolean externalOk = (score != null && score >= 800);

        return new GraduationResult(majorRequiredOk, generalRequiredOk, creditOk, externalOk);
    }
}
