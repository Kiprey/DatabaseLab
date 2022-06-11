package com.lab.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Teacher")
@Entity
public class Teacher {
    private String teacherName;
    @Id
    private String teacherID;
    private String facultyCode;


    public Teacher() {
    }

    public Teacher(String teacherName, String teacherID, String facultyCode) {
        this.teacherName = teacherName;
        this.teacherID = teacherID;
        this.facultyCode = facultyCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }
}
