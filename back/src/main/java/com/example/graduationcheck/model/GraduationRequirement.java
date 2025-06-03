package com.example.graduationcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class GraduationRequirement {
    @Id @GeneratedValue
    private Long id;

    private int totalCredit;
    private int majorCredit;
    private boolean englishRequirement;
}
