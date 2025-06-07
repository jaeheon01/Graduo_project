package com.example.graduationcheck.model;

import jakarta.persistence.*;

/**
 * DDL 스키마:
 * CREATE TABLE GRADUO.taken_lecture (
 *     id INT NOT NULL PRIMARY KEY,
 *     user_id INT NOT NULL,
 *     lecture_id VARCHAR(10) NOT NULL,
 *     taken_year INT NULL,
 *     score ENUM('A+', 'A0', 'B+', 'B0', 'C+', 'C0', 'P', 'F') NULL,
 *     FOREIGN KEY (user_id) REFERENCES GRADUO.user(user_id),
 *     FOREIGN KEY (lecture_id) REFERENCES GRADUO.lecture(lecture_id)
 * );
 */
@Entity
@Table(name = "taken_lecture", schema = "GRADUO")
public class TakenLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "lecture_id", length = 10)
    private String lectureId;

    @Column(name = "taken_year")
    private Integer takenYear;

    @Column(name = "score", length = 2)
    private String score;

    // ‘semester’ 컬럼은 DDL에 없으므로 제거했습니다.

    // Getter / Setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLectureId() {
        return lectureId;
    }
    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public Integer getTakenYear() {
        return takenYear;
    }
    public void setTakenYear(Integer takenYear) {
        this.takenYear = takenYear;
    }

    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
}
