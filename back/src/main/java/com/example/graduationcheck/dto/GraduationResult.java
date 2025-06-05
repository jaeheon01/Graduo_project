package com.example.graduationcheck.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GraduationResult {
    private boolean majorRequiredOk;
    private boolean generalRequiredOk;
    private boolean creditOk;
    private boolean externalOk;

    public boolean isPassed() {
        return majorRequiredOk && generalRequiredOk && creditOk && externalOk;
    }
}