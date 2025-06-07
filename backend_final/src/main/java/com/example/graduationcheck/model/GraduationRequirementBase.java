package com.example.graduationcheck.model;

import jakarta.persistence.*;

/**
 * DDL 스키마:
 * CREATE TABLE GRADUO.graduation_requirement_base (
 *     id INT NOT NULL PRIMARY KEY,
 *     department VARCHAR(50) NOT NULL,
 *     start_year INT NOT NULL,
 *     end_year INT NOT NULL,
 *     total_credit INT NOT NULL CHECK(total_credit > 0),
 *     distribution_area_credit INT NOT NULL CHECK(distribution_area_credit > 0)
 * );
 */
@Entity
@Table(name = "graduation_requirement_base", schema = "GRADUO")
public class GraduationRequirementBase {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "total_credit")
    private Integer totalCredit;

    @Column(name = "distribution_area_credit")
    private Integer distributionAreaCredit;

    // Getter / Setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getStartYear() {
        return startYear;
    }
    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }
    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getTotalCredit() {
        return totalCredit;
    }
    public void setTotalCredit(Integer totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Integer getDistributionAreaCredit() {
        return distributionAreaCredit;
    }
    public void setDistributionAreaCredit(Integer distributionAreaCredit) {
        this.distributionAreaCredit = distributionAreaCredit;
    }
}
