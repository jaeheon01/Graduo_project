package com.example.graduationcheck.model;

import jakarta.persistence.*;

/**
 * DDL 스키마:
 * CREATE TABLE GRADUO.graduation_req_basic_courses (
 *     id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 *     base_id     INT NOT NULL,
 *     course_name VARCHAR(100) NOT NULL,
 *     FOREIGN KEY (base_id)
 *       REFERENCES GRADUO.graduation_requirement_base(id)
 *       ON DELETE CASCADE
 * );
 */
@Entity
@Table(name = "graduation_req_basic_courses", schema = "GRADUO")
public class GraduationReqBasicCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "base_id")
    private Integer baseId;

    @Column(name = "course_name", length = 100)
    private String courseName;

    // Getter / Setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBaseId() {
        return baseId;
    }
    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
