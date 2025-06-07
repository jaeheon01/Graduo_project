package com.example.graduationcheck.model;

import jakarta.persistence.*;

@Entity
@Table(name = "major", schema = "GRADUO")
public class Major {

    @Id
    @Column(name = "lecture_id")
    private String lectureId;

    // 전공필수 여부
    @Column(name = "is_required")
    private Boolean isRequired;

    // 산학협력 여부 (Lecture 엔티티에는 이 필드 없음!)
    @Column(name = "is_industry")
    private Boolean isIndustry;

    public Major() { }

    public String getLectureId() {
        return lectureId;
    }
    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Boolean getIsIndustry() {
        return isIndustry;
    }
    public void setIsIndustry(Boolean isIndustry) {
        this.isIndustry = isIndustry;
    }
}
