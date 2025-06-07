package com.example.graduationcheck.model;

import jakarta.persistence.*;

/**
 * DDL 스키마:
 * CREATE TABLE GRADUO.user (
 *     user_id INT NOT NULL PRIMARY KEY,
 *     name VARCHAR(50) NULL,
 *     student_number VARCHAR(20) UNIQUE,
 *     department VARCHAR(50) NULL,
 *     entrance_year INT NULL,
 *     is_dual_degree BOOLEAN DEFAULT FALSE
 * );
 */
@Entity
@Table(name = "user", schema = "GRADUO")
public class User {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "student_number", length = 20, unique = true)
    private String studentNumber;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "entrance_year")
    private Integer entranceYear;

    @Column(name = "is_dual_degree")
    private Boolean isDualDegree;

    // Getter / Setter
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getEntranceYear() {
        return entranceYear;
    }
    public void setEntranceYear(Integer entranceYear) {
        this.entranceYear = entranceYear;
    }

    public Boolean getIsDualDegree() {
        return isDualDegree;
    }
    public void setIsDualDegree(Boolean isDualDegree) {
        this.isDualDegree = isDualDegree;
    }
}
