package com.example.graduationcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class User {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private String studentId;
}
