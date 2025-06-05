package com.example.graduationcheck.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraduationResult {
    private boolean totalCreditOk;
    private boolean majorCreditOk;
    private boolean requiredMajorCoursesOk;
    private boolean requiredGeneralCoursesOk;
    private boolean liberalAreaOk;
    private boolean externalLanguageOk;
    private boolean externalProgrammingOk;
    private List<String> retakeRecommendations;
    private List<String> missingCourses;

    public boolean isPassed() {
        return totalCreditOk && majorCreditOk && requiredMajorCoursesOk && requiredGeneralCoursesOk
                && liberalAreaOk && externalLanguageOk && externalProgrammingOk;
    }
}
