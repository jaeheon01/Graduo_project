package com.example.graduationcheck.dto;

public class SignUpRequest {
    private Integer userId;
    private String name;
    private String studentNumber;
    private String department;
    private Integer entranceYear;
    private Boolean isDualDegree;

    // 외부성적 통합
    private String engScoreType;        // TOEIC, TEPS, OPIC, TOEIC_SPEAKING
    private Integer engScore;           // 점수
    private String externalScoreType;   // TOPCIT, APC
    private Integer externalScore;      // 점수
    private String aquisitionDate;      // (옵션) yyyy-MM-dd

    // --- Getter/Setter ---
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Integer getEntranceYear() { return entranceYear; }
    public void setEntranceYear(Integer entranceYear) { this.entranceYear = entranceYear; }
    public Boolean getIsDualDegree() { return isDualDegree; }
    public void setIsDualDegree(Boolean isDualDegree) { this.isDualDegree = isDualDegree; }
    public String getEngScoreType() { return engScoreType; }
    public void setEngScoreType(String engScoreType) { this.engScoreType = engScoreType; }
    public Integer getEngScore() { return engScore; }
    public void setEngScore(Integer engScore) { this.engScore = engScore; }
    public String getExternalScoreType() { return externalScoreType; }
    public void setExternalScoreType(String externalScoreType) { this.externalScoreType = externalScoreType; }
    public Integer getExternalScore() { return externalScore; }
    public void setExternalScore(Integer externalScore) { this.externalScore = externalScore; }
    public String getAquisitionDate() { return aquisitionDate; }
    public void setAquisitionDate(String aquisitionDate) { this.aquisitionDate = aquisitionDate; }
}
