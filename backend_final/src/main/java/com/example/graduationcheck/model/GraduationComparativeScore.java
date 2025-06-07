package com.example.graduationcheck.model;

import jakarta.persistence.*;

/**
 * DDL 스키마:
 * CREATE TABLE GRADUO.graduation_comparative_score (
 *     id INT NOT NULL PRIMARY KEY,
 *     toeic INT NULL CHECK (toeic > 0),
 *     teps INT NULL CHECK (teps > 0),
 *     toeic_speaking INT NULL CHECK (toeic_speaking > 0),
 *     opic INT NULL CHECK (opic > 0),
 *     topcit INT NULL CHECK (topcit > 0),
 *     apc INT NULL CHECK (apc > 0),
 *     FOREIGN KEY (id) REFERENCES GRADUO.graduation_requirement_base(id)
 * );
 */
@Entity
@Table(name = "graduation_comparative_score", schema = "GRADUO")
public class GraduationComparativeScore {

    @Id
    @Column(name = "id")
    private Integer id;  // FK → graduation_requirement_base.id

    @Column(name = "toeic")
    private Integer toeic;

    @Column(name = "teps")
    private Integer teps;

    @Column(name = "toeic_speaking")
    private Integer toeicSpeaking;

    @Column(name = "opic")
    private Integer opic;

    @Column(name = "topcit")
    private Integer topcit;

    @Column(name = "apc")
    private Integer apc;

    // Getter / Setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToeic() {
        return toeic;
    }
    public void setToeic(Integer toeic) {
        this.toeic = toeic;
    }

    public Integer getTeps() {
        return teps;
    }
    public void setTeps(Integer teps) {
        this.teps = teps;
    }

    public Integer getToeicSpeaking() {
        return toeicSpeaking;
    }
    public void setToeicSpeaking(Integer toeicSpeaking) {
        this.toeicSpeaking = toeicSpeaking;
    }

    public Integer getOpic() {
        return opic;
    }
    public void setOpic(Integer opic) {
        this.opic = opic;
    }

    public Integer getTopcit() {
        return topcit;
    }
    public void setTopcit(Integer topcit) {
        this.topcit = topcit;
    }

    public Integer getApc() {
        return apc;
    }
    public void setApc(Integer apc) {
        this.apc = apc;
    }
}
