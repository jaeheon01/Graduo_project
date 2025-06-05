package com.example.graduationcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class GraduationComparativeScore {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scoreType; // toeic, apc 등
    private int requiredScore;
}
