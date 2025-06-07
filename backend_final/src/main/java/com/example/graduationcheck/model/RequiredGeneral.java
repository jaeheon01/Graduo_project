package com.example.graduationcheck.model;

import jakarta.persistence.*;

/**
 * DDL 스키마:
 * CREATE TABLE GRADUO.required_general (
 *     lecture_id VARCHAR(10) NOT NULL PRIMARY KEY,
 *     is_field_required BOOLEAN DEFAULT FALSE,
 *     subject_field ENUM('역사와 철학', '문학과 예술', '인간과 사회', '자연과 과학', '연결과 통합', 'Others') NULL,
 *     FOREIGN KEY (lecture_id) REFERENCES GRADUO.lecture(lecture_id)
 * );
 */
@Entity
@Table(name = "required_general", schema = "GRADUO")
public class RequiredGeneral {

    @Id
    @Column(name = "lecture_id", length = 10)
    private String lectureId;

    @Column(name = "is_field_required")
    private Boolean isFieldRequired;

    @Column(name = "subject_field")
    private String subjectField;

    // Getter / Setter
    public String getLectureId() {
        return lectureId;
    }
    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public Boolean getIsFieldRequired() {
        return isFieldRequired;
    }
    public void setIsFieldRequired(Boolean isFieldRequired) {
        this.isFieldRequired = isFieldRequired;
    }

    public String getSubjectField() {
        return subjectField;
    }
    public void setSubjectField(String subjectField) {
        this.subjectField = subjectField;
    }
}
