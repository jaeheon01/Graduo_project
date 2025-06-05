package com.example.graduationcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class GraduationRequirementBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String department;
    private int startYear;
    private int endYear;
    private int totalCredit;
    private int distributionAreaCredit;
}