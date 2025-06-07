package com.example.graduationcheck.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lecture", schema = "GRADUO")
public class Lecture {

    @Id
    @Column(name = "lecture_id")
    private String lectureId;

    private String name;
    private Integer credit;
    private String code;
    private String year;

    // “lecture_department” 컬럼만 남겨둡니다.
    @Column(name = "lecture_department")
    private String lectureDepartment;

    // ────────────────────────────────────────────────────────────
    // getters / setters

    public String getLectureId() {
        return lectureId;
    }
    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredit() {
        return credit;
    }
    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    // 새로 추가: 학과 정보를 가져오기 위한 메서드
    public String getLectureDepartment() {
        return lectureDepartment;
    }
    public void setLectureDepartment(String lectureDepartment) {
        this.lectureDepartment = lectureDepartment;
    }
}
