package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Class")
@Entity
public class Major {
    private String majorName;
    @Id
    private String majorCode;
    private String facultyCode;
    private String degreeLevel;
    private String graduationCredits;

    public Major() {

    }

    public Major(String majorName, String majorCode, String facultyCode, String degreeLevel, String graduationCredits) {
        this.majorName = majorName;
        this.majorCode = majorCode;
        this.facultyCode = facultyCode;
        this.degreeLevel = degreeLevel;
        this.graduationCredits = graduationCredits;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(String degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public String getGraduationCredits() {
        return graduationCredits;
    }

    public void setGraduationCredits(String graduationCredits) {
        this.graduationCredits = graduationCredits;
    }
}
