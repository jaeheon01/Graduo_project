package com.example.graduationcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ChangeNamedLecture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prevLectureName;
    private String currentLectureName;
}