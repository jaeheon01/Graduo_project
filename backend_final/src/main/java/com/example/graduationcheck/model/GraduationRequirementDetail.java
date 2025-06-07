package com.example.graduationcheck.model;

import jakarta.persistence.*;

/**
 * DDL 스키마:
 * CREATE TABLE GRADUO.graduation_requirement_detail (
 *     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 *     base_id INT NOT NULL,
 *     is_dual_degree BOOLEAN NOT NULL,
 *     major_credit INT NOT NULL CHECK(major_credit > 0),
 *     industrial_course_count INT NOT NULL CHECK(industrial_course_count > 0),
 *     FOREIGN KEY (base_id) REFERENCES GRADUO.graduation_requirement_base(id)
 * );
 */
@Entity
@Table(name = "graduation_requirement_detail", schema = "GRADUO")
public class GraduationRequirementDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "base_id")
    private Integer baseId;

    @Column(name = "is_dual_degree")
    private Boolean isDualDegree;

    @Column(name = "major_credit")
    private Integer majorCredit;

    @Column(name = "industrial_course_count")
    private Integer industrialCourseCount;

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

    public Boolean getIsDualDegree() {
        return isDualDegree;
    }
    public void setIsDualDegree(Boolean isDualDegree) {
        this.isDualDegree = isDualDegree;
    }

    public Integer getMajorCredit() {
        return majorCredit;
    }
    public void setMajorCredit(Integer majorCredit) {
        this.majorCredit = majorCredit;
    }

    public Integer getIndustrialCourseCount() {
        return industrialCourseCount;
    }
    public void setIndustrialCourseCount(Integer industrialCourseCount) {
        this.industrialCourseCount = industrialCourseCount;
    }
}
