package com.example.graduationcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String subjectField;
    private String classification;
    private String department;
}
