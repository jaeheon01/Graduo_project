package com.example.graduationcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranscriptCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    private String grade;

    private int credit;

    private Long userId;

    private String subjectField;      // 교양 영역 등
    private String classification;    // 이수 구분 (전공/교양 등)
}
