package com.example.graduationcheck.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "external_score", schema = "GRADUO")
public class ExternalScore {

    // 실제 테이블의 PK 칼럼을 그대로 매핑해야 합니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "external_score_id")
    private Integer externalScoreId;

    // 여기에 @Column(name="user_id") 를 달아서, userId 칼럼과 매핑
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "eng_score_type")
    private String engScoreType;        // e.g. "TOEIC", "TEPS", "OPIC", "TOEIC_SPEAKING"

    @Column(name = "eng_score")
    private Integer engScore;           // 언어 점수

    @Column(name = "external_score_type")
    private String externalScoreType;   // e.g. "TOPCIT", "APC"

    @Column(name = "external_score")
    private Integer externalScore;      // 프로그래밍 역량 점수

    @Column(name = "aquisition_date")
    private LocalDate aquisitionDate;

    // ──────────────────────────────────────────────────
    // 기본 생성자
    public ExternalScore() { }

    // ──────────────────────────────────────────────────
    // getters / setters

    public Integer getExternalScoreId() {
        return externalScoreId;
    }
    public void setExternalScoreId(Integer externalScoreId) {
        this.externalScoreId = externalScoreId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEngScoreType() {
        return engScoreType;
    }
    public void setEngScoreType(String engScoreType) {
        this.engScoreType = engScoreType;
    }

    public Integer getEngScore() {
        return engScore;
    }
    public void setEngScore(Integer engScore) {
        this.engScore = engScore;
    }

    public String getExternalScoreType() {
        return externalScoreType;
    }
    public void setExternalScoreType(String externalScoreType) {
        this.externalScoreType = externalScoreType;
    }

    public Integer getExternalScore() {
        return externalScore;
    }
    public void setExternalScore(Integer externalScore) {
        this.externalScore = externalScore;
    }

    public LocalDate getAquisitionDate() {
        return aquisitionDate;
    }
    public void setAquisitionDate(LocalDate aquisitionDate) {
        this.aquisitionDate = aquisitionDate;
    }
}
