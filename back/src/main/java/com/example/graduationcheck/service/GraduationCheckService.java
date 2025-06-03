package com.example.graduationcheck.service;

import com.example.graduationcheck.model.TranscriptCourse;
import com.example.graduationcheck.repository.TranscriptRepository;
import com.example.graduationcheck.repository.GraduationRequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraduationCheckService {

    @Autowired
    private TranscriptRepository transcriptRepository;

    @Autowired
    private GraduationRequirementRepository requirementRepository;

    public Map<String, Object> checkGraduation(Long userId) {
        List<TranscriptCourse> courses = transcriptRepository.findByUserId(userId);
        int totalCredits = courses.stream().mapToInt(TranscriptCourse::getCredit).sum();
        boolean isPossible = totalCredits >= 130; // 예시 요건

        Map<String, Object> result = new HashMap<>();
        result.put("graduationPossible", isPossible);
        result.put("totalCredits", totalCredits);
        return result;
    }
}
