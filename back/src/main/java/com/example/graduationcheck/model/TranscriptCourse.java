package com.example.graduationcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranscriptCourse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String grade;
    private int credit;

    private Long userId;  // 외래키 대신 단순 ID로 처리
}
