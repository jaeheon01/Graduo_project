package com.example.graduationcheck.model;

import jakarta.persistence.*;

@Entity
@Table(name = "change_named_lecture", schema = "GRADUO")
public class ChangeNamedLecture {

    @Id
    @Column(name = "prev_lecture_name", length = 100)
    private String prevLectureName;

    @Column(name = "current_lecture_name", length = 100)
    private String currentLectureName;

    // Getter / Setter
    public String getPrevLectureName() {
        return prevLectureName;
    }
    public void setPrevLectureName(String prevLectureName) {
        this.prevLectureName = prevLectureName;
    }

    public String getCurrentLectureName() {
        return currentLectureName;
    }
    public void setCurrentLectureName(String currentLectureName) {
        this.currentLectureName = currentLectureName;
    }
}
