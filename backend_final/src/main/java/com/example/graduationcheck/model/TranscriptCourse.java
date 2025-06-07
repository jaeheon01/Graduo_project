package com.example.graduationcheck.model;

/**
 * PDF 파싱 결과를 잠시 담는 DTO용 클래스
 * 테이블로 관리할 필요가 없으므로 @Entity, @Table 애노테이션을 제거합니다.
 */
public class TranscriptCourse {

    private Long id;            // DTO 내부에서 원본 로우를 구분하려면 남겨둬도 되고, 없어도 무방합니다.
    private Integer userId;
    private Integer takenYear;
    private String semester;
    private String courseName;
    private String grade;

    // ────────────────────────────────────────────────────────────────
    // getters / setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    // ────────────────────────────────────────────────────────────────
}
