package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Faculty")
@Entity
public class Faculty {
    private String facultyName;
    @Id
    private String facultyCode;
    private String facultyAddress;
    private String facultyTeleno;

    public Faculty() {
    }

    public Faculty(String facultyName, String facultyCode, String facultyAddress, String facultyTeleno) {
        this.facultyName = facultyName;
        this.facultyCode = facultyCode;
        this.facultyAddress = facultyAddress;
        this.facultyTeleno = facultyTeleno;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getFacultyAddress() {
        return facultyAddress;
    }

    public void setFacultyAddress(String facultyAddress) {
        this.facultyAddress = facultyAddress;
    }

    public String getFacultyTeleno() {
        return facultyTeleno;
    }

    public void setFacultyTeleno(String facultyTeleno) {
        this.facultyTeleno = facultyTeleno;
    }
}
