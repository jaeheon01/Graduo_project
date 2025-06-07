package com.example.graduationcheck.dto;

public class TakenLectureWithNameDTO {
    private String lectureId;
    private String lectureName;
    private String score;
    private Integer takenYear;
    private String semester;

    // ────────────────────────────────────────────────────────────────
    // getters / setters

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getTakenYear() {
        return takenYear;
    }

    public void setTakenYear(Integer takenYear) {
        this.takenYear = takenYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
    // ────────────────────────────────────────────────────────────────
}
